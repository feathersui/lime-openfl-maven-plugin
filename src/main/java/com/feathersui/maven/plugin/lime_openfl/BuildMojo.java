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
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Builds a <a href="https://lime.software/">Lime</a>,
 * <a href="https://openfl.org/">OpenFL</a>, or
 * <a href="https://feathersui.com/">Feathers UI</a> project.
 * 
 * <p>
 * The following example demonstrates how to add the <strong>build</strong> goal
 * to your Maven <em>pom.xml</em> file.
 * </p>
 * 
 * <pre>
 * {@code
 * <build>
 *   <plugins>
 *     <plugin>
 *       <groupId>com.feathersui.maven.plugins</groupId>
 *       <artifactId>lime-openfl-maven-plugin</artifactId>
 *       <version>1.0.0-SNAPSHOT</version>
 *       <executions>
 *         <execution>
 *           <goals>
 *             <!-- add this goal to build your Lime project -->
 *             <goal>build</goal>
 *           </goals>
 *         </execution>
 *       </executions>
 *     </plugin>
 *   </plugins>
 * </build>
 * }
 * </pre>
 * 
 * @see <a href=
 *      "https://lime.software/docs/command-line-tools/basic-commands/#lime-build"><strong>lime
 *      build</strong> command</a>
 */
@Mojo(name = "build", defaultPhase = LifecyclePhase.COMPILE)
public class BuildMojo extends AbstractMojo {
	/**
	 * Optionally specify a custom path to the Haxelib executable. If not set,
	 * the build will assume that a Haxelib executable can be found on the
	 * system path.
	 */
	@Parameter(property = "lime.haxelib")
	public File haxelibExecutable;

	/**
	 * Optionally specify a custom path to a Lime <em>project.xml</em> file.
	 * If not set, will fall back to searching for a <em>project.xml</em> file
	 * in the same directory as the Maven <em>pom.xml</em> file.
	 * 
	 * <p>
	 * If using {@link GenerateProjectXmlMojo}, this field will be populated
	 * with the path to the generated <em>project.xml</em> file.
	 * </p>
	 */
	@Parameter(defaultValue = "project.xml", property = "lime.projectFile")
	public File projectFile;

	/**
	 * Specify the Lime build target, such as "html5", "hl", "neko", "ios",
	 * "android", "windows", "mac", or "linux".
	 */
	@Parameter(defaultValue = "html5", property = "lime.target")
	public String target;

	/**
	 * Specify if the build is debug instead of release using the {@code -debug}
	 * command line option.
	 */
	@Parameter(defaultValue = "false", property = "lime.debug")
	public boolean isDebug;

	/**
	 * Specify if the build is final instead of release using the {@code -final}
	 * command line option.
	 */
	@Parameter(defaultValue = "false", property = "lime.final")
	public boolean isFinal;

	/**
	 * Specify additional {@code <haxedef/>} values using the {@code --haxedef}
	 * command line option.
	 */
	@Parameter
	public String[] additionalHaxedefs;

	/**
	 * Specify additional {@code <haxelib/>} values using the {@code --haxelib}
	 * command line option.
	 */
	@Parameter
	public String[] additionalHaxelibs;

	/**
	 * Specify additional {@code <source/>} values using the {@code --source}
	 * command line option.
	 */
	@Parameter
	public String[] additionalSources;

	/**
	 * Specify additional {@code <dependency/>} values using the
	 * {@code --dependency} command line option.
	 */
	@Parameter
	public String[] additionalDependencies;

	/**
	 * Optionally sets the name of the output file using the {@code --app-file}
	 * command line option.
	 */
	@Parameter
	public String outputFileName;

	@Parameter(defaultValue = "${basedir}", required = true, readonly = true)
	private File basedir;

	@Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
	private File buildDirectory;

	/**
	 * Builds the Lime project.
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (isDebug && isFinal) {
			throw new MojoFailureException("Cannot set both isDebug and isFinal parameters for Lime project");
		}
		try {
			checkHaxelibs();
			build();
		} catch (Exception e) {
			throw new MojoExecutionException("Fatal error building Lime project", e);
		}
	}

	private String getHaxelibPath() {
		String result = "haxelib";
		if (haxelibExecutable != null) {
			result = haxelibExecutable.getAbsolutePath();
		}
		return result;
	}

	private void checkHaxelibs() throws Exception {
		getLog().debug("Checking libraries...");
		boolean foundLime = false;
		Document projectDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(projectFile);
		NodeList haxelibElements = projectDocument.getDocumentElement().getElementsByTagName("haxelib");
		for (int i = 0; i < haxelibElements.getLength(); i++) {
			Element haxelibElement = (Element) haxelibElements.item(i);
			if (!haxelibElement.hasAttribute("name")) {
				continue;
			}
			String name = haxelibElement.getAttribute("name");
			if (name.length() == 0) {
				continue;
			}
			if (!foundLime && name.equals("lime")) {
				foundLime = true;
			}
			String version = null;
			if (haxelibElement.hasAttribute("version")) {
				version = haxelibElement.getAttribute("version");
			}
			checkHaxelib(name, version);
		}
		if (additionalHaxelibs != null) {
			for (String haxelib : additionalHaxelibs) {
				if (!foundLime && haxelib.equals("lime")) {
					foundLime = true;
				}
				checkHaxelib(haxelib, null);
			}
		}
		if (!foundLime) {
			checkHaxelib("lime", null);
		}
	}

	private void checkHaxelib(String name, String requiredVersion) throws CommandLineException, MojoFailureException {
		getLog().debug("Checking library: " + name + " " + requiredVersion);
		Commandline commandLine = new Commandline();
		commandLine.setWorkingDirectory(basedir);
		commandLine.setExecutable(getHaxelibPath());
		commandLine.createArg().setValue("path");
		commandLine.createArg().setValue(name);

		CommandLineUtils.StringStreamConsumer systemOut = new CommandLineUtils.StringStreamConsumer();
		CommandLineUtils.StringStreamConsumer systemErr = new CommandLineUtils.StringStreamConsumer();

		int exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (exitCode == 0) {
			if (requiredVersion == null) {
				getLog().debug("Library is installed and no specific version is required: " + name);
				// any version is fine
				return;
			}
			String haxelibPathOutput = systemOut.getOutput();
			String[] parts = haxelibPathOutput.split("\n");
			String existingVersion = null;
			for (String part : parts) {
				if (part.startsWith("-D " + name + "=")) {
					existingVersion = part.substring(name.length() + 4);
					break;
				}
			}
			if (requiredVersion.equals(existingVersion)) {
				getLog().debug("Library is installed with correct version: " + name + " " + existingVersion);
				return;
			}
		}
		getLog().debug("Library not installed: " + name);
		installHaxelib(name, requiredVersion);
	}

	private void installHaxelib(String name, String requiredVersion) throws CommandLineException, MojoFailureException {
		getLog().debug("Installing library: " + name + " " + requiredVersion);
		Commandline commandLine = new Commandline();
		commandLine.setWorkingDirectory(basedir);
		commandLine.setExecutable(getHaxelibPath());
		commandLine.createArg().setValue("install");
		commandLine.createArg().setValue(name);
		if (requiredVersion != null) {
			commandLine.createArg().setValue(requiredVersion);
		}
		commandLine.createArg().setValue("--always");
		commandLine.createArg().setValue("--quiet");

		CommandLineUtils.StringStreamConsumer systemOut = new CommandLineUtils.StringStreamConsumer();
		CommandLineUtils.StringStreamConsumer systemErr = new CommandLineUtils.StringStreamConsumer();

		int exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (exitCode == 0) {
			return;
		}
		throw new MojoFailureException("Lime build failure. Failed to install Haxelib: " + name);
	}

	private void build() throws CommandLineException, MojoFailureException {
		getLog().debug("Building project: " + projectFile.getParentFile().getName());
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
		commandLine.createArg().setValue("--app-path=" + buildDirectory.getAbsolutePath());
		if (outputFileName != null) {
			commandLine.createArg().setValue("--app-file=" + outputFileName);
		}
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

		WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));
		WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.err));

		int exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		if (exitCode == 0) {
			return;
		}
		throw new MojoFailureException("Lime build failure. Process exited with code: " + exitCode);
	}
}