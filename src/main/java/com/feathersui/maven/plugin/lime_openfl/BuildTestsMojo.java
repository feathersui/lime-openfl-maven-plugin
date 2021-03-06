/*
Copyright 2022 Bowler Hat LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.feathersui.maven.plugin.lime_openfl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.feathersui.maven.plugin.utils.LogOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * Detects and builds the <a href="https://lib.haxe.org/p/utest/">utest</a>
 * tests for a <a href="https://lime.software/">Lime</a>,
 * <a href="https://openfl.org/">OpenFL</a>, or
 * <a href="https://feathersui.com/">Feathers UI</a> project.
 * 
 * <p>
 * The tests should be located in the <em>src/test/haxe</em> directory.
 * </p>
 */
@Mojo(name = "test-build", defaultPhase = LifecyclePhase.TEST_COMPILE)
public class BuildTestsMojo extends BaseBuildMojo {
	private static final Pattern importPattern = Pattern.compile("\\bimport\\s+utest\\.Test\\s*;");
	private static final Pattern extendsPatternWithImport = Pattern
			.compile("\\bclass\\s+\\w+\\s+extends\\s+(?:utest\\.)?Test\\b");
	private static final Pattern extendsPatternWithoutImport = Pattern
			.compile("\\bclass\\s+\\w+\\s+extends\\s+utest\\.Test\\b");

	@Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
	private File buildDirectory;

	@Parameter(defaultValue = "${project.build.directory}/utest", required = true, readonly = true)
	private File testBuildDirectory;

	@Parameter(defaultValue = "${basedir}/src/test/haxe", required = true, readonly = true)
	private File testSrcDirectory;

	@Parameter(defaultValue = "${project.build.directory}/utest/generated-sources", required = true, readonly = true)
	private File testGeneratedSrcDirectory;

	@Parameter(defaultValue = "${project.build.directory}/utest/html5/bin/index.html", required = true, readonly = true)
	private File testIndexFile;

	@Parameter(property = "maven.test.skip", readonly = true)
	private boolean skip;

	@Override
	protected void checkHaxelibs() throws Exception {
		super.checkHaxelibs();
		checkHaxelib("utest", null);
	}

	@Override
	protected void build() throws CommandLineException, MojoFailureException, MojoExecutionException {
		if (skip) {
			getLog().info("Not building test sources");
			return;
		}

		try {
			boolean hasTests = generateTestSources();
			if (!hasTests) {
				project.getProperties().setProperty("maven.test.skip", "true");
				return;
			}
		} catch (Exception e) {
			throw new MojoFailureException("Failed to generate Lime tests sources");
		}

		getLog().debug("Building tests: " + basedir.getName());

		Commandline commandLine = new Commandline();
		commandLine.setWorkingDirectory(basedir);
		commandLine.setExecutable(getHaxelibPath());
		commandLine.createArg().setValue("run");
		commandLine.createArg().setValue("lime");
		commandLine.createArg().setValue("build");
		commandLine.createArg().setValue(projectFile.getAbsolutePath());
		commandLine.createArg().setValue(target);
		if (isDebug) {
			commandLine.createArg().setValue("-debug");
		}
		if (isFinal) {
			commandLine.createArg().setValue("-final");
		}
		commandLine.createArg().setValue("--source=" + testSrcDirectory.getAbsolutePath());
		commandLine.createArg().setValue("--source=" + testGeneratedSrcDirectory.getAbsolutePath());
		commandLine.createArg().setValue("--app-path=" + testBuildDirectory.getAbsolutePath());
		commandLine.createArg().setValue("--app-file=TestsMain");
		commandLine.createArg().setValue("--app-main=TestsMain");
		commandLine.createArg().setValue("--haxelib=utest");
		if (additionalSources != null) {
			for (String current : additionalSources) {
				commandLine.createArg().setValue("--source=" + current);
			}
		}
		if (additionalDependencies != null) {
			for (String current : additionalDependencies) {
				commandLine.createArg().setValue("--dependency=" + current);
			}
		}
		if (additionalHaxedefs != null) {
			for (String current : additionalHaxedefs) {
				commandLine.createArg().setValue("--haxedef=" + current);
			}
		}
		if (additionalHaxelibs != null) {
			for (String current : additionalHaxelibs) {
				commandLine.createArg().setValue("--haxelib=" + current);
			}
		}

		WriterStreamConsumer systemOut = new WriterStreamConsumer(
				new OutputStreamWriter(new LogOutputStream(getLog(), false)));
		WriterStreamConsumer systemErr = new WriterStreamConsumer(
				new OutputStreamWriter(new LogOutputStream(getLog(), true)));

		int exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (exitCode != 0) {
			throw new MojoFailureException("Lime test build failure. Process exited with code: " + exitCode);
		}

		if ("html5".equals(target)) {
			generateTestIndex();
		}
	}

	protected boolean generateTestSources() throws MojoExecutionException, IOException {
		if (!testSrcDirectory.exists()) {
			return false;
		}
		Path testSrcDirectoryPath = testSrcDirectory.toPath();
		List<String> testQualifiedNames = Files.walk(testSrcDirectoryPath).map(Path::toFile).filter((File file) -> {
			if (!file.getName().endsWith(".hx")) {
				return false;
			}

			String fileText;
			try {
				fileText = FileUtils.readFileToString(file, Charset.forName("utf-8"));
			} catch (IOException e) {
				return false;
			}

			Matcher importMatcher = importPattern.matcher(fileText);
			boolean hasImport = importMatcher.find();

			boolean hasExtends = false;
			if (hasImport) {
				Matcher extendsMatcher = extendsPatternWithImport.matcher(fileText);
				hasExtends = extendsMatcher.find();
			} else {
				Matcher extendsMatcher = extendsPatternWithoutImport.matcher(fileText);
				hasExtends = extendsMatcher.find();
			}

			if (!hasExtends) {
				return false;
			}

			return true;
		}).map(File::toPath).map(path -> testSrcDirectoryPath.relativize(path).toString().replace("/", ".")
				.replace("\\", ".").replace(".hx", "")).collect(Collectors.toList());

		if (testQualifiedNames.size() == 0) {
			return false;
		}

		StringBuilder mainBuilder = new StringBuilder()
				.append("import openfl.display.Sprite;\n")
				.append("import utest.Runner;\n")
				.append("import utest.ui.Report;\n")
				.append("import com.example.SampleTestCase;\n")
				.append("class TestsMain extends Sprite {\n")
				.append("  public function new() {\n")
				.append("    super();\n")
				.append("    var runner = new Runner();\n");
		for (String qualifiedName : testQualifiedNames) {
			mainBuilder.append("    runner.addCase(new ")
					.append(qualifiedName)
					.append("());\n");
		}
		mainBuilder
				.append("    #if html5\n")
				.append("    new utest.ui.text.PrintReport(runner);\n")
				.append("    var aggregator = new utest.ui.common.ResultAggregator(runner, true);\n")
				.append("    aggregator.onComplete.add(function(result:utest.ui.common.PackageResult):Void {\n")
				.append("  	  Reflect.setField(js.Lib.global, \"utestResult\", result);\n")
				.append("    });\n")
				.append("    #else\n")
				.append("    Report.create(runner);\n")
				.append("    #end\n")
				.append("    runner.run();\n")
				.append("  }\n")
				.append("}\n");
		File testGeneratedMainFile = new File(testGeneratedSrcDirectory, "TestsMain.hx");
		try {
			FileUtils.write(testGeneratedMainFile, mainBuilder.toString(), Charset.forName("utf-8"));
		} catch (Exception e) {
			throw new MojoExecutionException("Fatal error generating Lime tests project file", e);
		}

		return true;
	}

	private void generateTestIndex() throws MojoExecutionException {
		StringBuilder indexBuilder = new StringBuilder()
				.append("<!DOCTYPE html>\n")
				.append("<html lang=\"en\">\n")
				.append("  <head>\n")
				.append("    <meta charset=\"utf-8\">\n")
				.append("    <title>lime-openfl-maven-plugin Test Runner</title>\n")
				.append("    <script type=\"text/javascript\" src=\"./TestsMain.js\"></script>\n")
				.append("  </head>\n")
				.append("  <body>\n")
				.append("    <noscript>Please enable JavaScript in your web browser to run tests.</noscript>\n")
				.append("    <script type=\"text/javascript\">\n")
				.append("      lime.$scripts[\"TestsMain\"]();\n")
				.append("    </script>\n")
				.append("  </body>\n")
				.append("</html>");
		try {
			FileUtils.write(testIndexFile, indexBuilder.toString(), Charset.forName("utf-8"));
		} catch (Exception e) {
			throw new MojoExecutionException("Fatal error generating Lime tests project file", e);
		}
	}
}