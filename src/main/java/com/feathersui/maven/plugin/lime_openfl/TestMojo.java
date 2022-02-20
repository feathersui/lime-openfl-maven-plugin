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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * 
 */
@Mojo(name = "test", defaultPhase = LifecyclePhase.TEST)
public class TestMojo extends BaseMojo {
	@Parameter(defaultValue = "${project.build.directory}/utest", required = true, readonly = true)
	private File testBuildDirectory;

	@Parameter(defaultValue = "${basedir}/src/test/haxe", required = true, readonly = true)
	private File testSrcDirectory;

	@Parameter(defaultValue = "${maven.test.skip}", readonly = true)
	private boolean skip;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (skip) {
			getLog().info("Not running tests");
			return;
		}

		Commandline commandLine = new Commandline();
		commandLine.setWorkingDirectory(basedir);
		commandLine.setExecutable(getHaxelibPath());
		commandLine.createArg().setValue("run");
		commandLine.createArg().setValue("lime");
		commandLine.createArg().setValue("run");
		commandLine.createArg().setValue(projectFile.getAbsolutePath());
		commandLine.createArg().setValue(target);
		commandLine.createArg().setValue("--app-path=" + testBuildDirectory.getAbsolutePath());
		commandLine.createArg().setValue("--app-file=TestsMain");

		WriterStreamConsumer systemOut = new WriterStreamConsumer(new OutputStreamWriter(System.out));
		WriterStreamConsumer systemErr = new WriterStreamConsumer(new OutputStreamWriter(System.err));

		int exitCode = 1;
		try {
			exitCode = CommandLineUtils.executeCommandLine(commandLine, systemOut, systemErr);
		} catch (Exception e) {
			throw new MojoExecutionException("Fatal error running Lime tests", e);
		}
		if (exitCode != 0) {
			throw new MojoFailureException("Lime test build failure. Process exited with code: " + exitCode);
		}
	}
}
