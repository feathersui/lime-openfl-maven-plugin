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

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

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
public class BuildMojo extends BaseBuildMojo {
	/**
	 * Optionally sets the name of the output file using the {@code --app-file}
	 * command line option.
	 */
	@Parameter
	public String outputFileName;

	@Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
	private File buildDirectory;

	@Override
	protected void build() throws CommandLineException, MojoFailureException {
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