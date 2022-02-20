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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * The base class for all Lime/OpenFL goals.
 */
public abstract class BaseMojo extends AbstractMojo {
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

	@Parameter(defaultValue = "${basedir}", required = true, readonly = true)
	protected File basedir;

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	protected MavenProject project;

	protected String getHaxelibPath() {
		String result = "haxelib";
		if (haxelibExecutable != null) {
			result = haxelibExecutable.getAbsolutePath();
		}
		return result;
	}

}
