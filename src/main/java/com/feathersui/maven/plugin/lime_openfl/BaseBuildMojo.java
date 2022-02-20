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

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The base class for Lime/OpenFL build goals.
 */
public abstract class BaseBuildMojo extends BaseMojo {
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

	protected abstract void build() throws CommandLineException, MojoExecutionException, MojoFailureException;

	protected void checkHaxelibs() throws Exception {
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

	protected void checkHaxelib(String name, String requiredVersion) throws CommandLineException, MojoFailureException {
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

	protected void installHaxelib(String name, String requiredVersion)
			throws CommandLineException, MojoFailureException {
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
}
