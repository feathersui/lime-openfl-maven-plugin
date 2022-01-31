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

/**
 * Defines a Lime project.
 * 
 * @see https://lime.software/docs/project-files/xml-format/
 */
public class LimeProject {
	/**
	 * Represents the <app> section of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#app
	 */
	public App app = null;

	/**
	 * Represents the <meta> section of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#meta
	 */
	public Meta meta = null;

	/**
	 * Represents the <window> section of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#window
	 */
	public Window window = null;

	/**
	 * Represents the &lt;source&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#source
	 */
	public Source[] sources = null;

	/**
	 * Represents the &lt;haxelib&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#haxelib
	 */
	public Haxelib[] haxelibs = null;

	/**
	 * Represents the &lt;haxedef&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#haxedef
	 */
	public HaxeDef[] haxedefs = null;

	/**
	 * Represents the &lt;haxeflag&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#haxeflag
	 */
	public HaxeFlag[] haxeflags = null;

	/**
	 * Represents the &lt;language&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#language
	 */
	public Language[] languages = null;

	/**
	 * Represents the &lt;dependency&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#dependency
	 */
	public Dependency[] dependencies = null;

	/**
	 * Represents the &lt;template&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#template
	 */
	public Template[] templates = null;

	/**
	 * Represents the &lt;include&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#include
	 */
	public Include[] includes = null;

	/**
	 * Represents the &lt;icon&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#icon
	 */
	public Icon[] icons = null;

	/**
	 * Represents the &lt;assets&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#assets
	 */
	public Assets[] assets = null;

	/**
	 * Represents the &lt;library&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#library
	 */
	public Library[] libraries = null;

	/**
	 * Represents the &lt;java&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#java
	 */
	public JavaSource[] java = null;

	/**
	 * Represents the &lt;launchImage&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#launchimage
	 */
	public LaunchImage launchImage = null;

	/**
	 * Represents the &lt;certificate&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#certificate
	 */
	public Certificate[] certificates = null;

	/**
	 * Represents the &lt;ndll&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#ndll
	 */
	public Ndll[] ndlls = null;

	/**
	 * Represents the &lt;path&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#path
	 */
	public SystemPath[] paths = null;

	/**
	 * Represents the &lt;sample&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#sample
	 */
	public Sample[] samples = null;

	/**
	 * Represents the &lt;setenv&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#setenv
	 */
	public SetEnv[] setenvs = null;

	/**
	 * Represents the &lt;architecture&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#architecture
	 */
	public Architecture[] architectures = null;

	/**
	 * Represents the &lt;prebuild&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#prebuild
	 */
	public PreBuild[] prebuilds = null;

	/**
	 * Represents the &lt;postbuild&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#postbuild
	 */
	public PostBuild[] postbuilds = null;

	/**
	 * Represents the &lt;section&gt; element of the Lime project XML format.
	 * 
	 * @see https://lime.software/docs/project-files/xml-format/#section
	 */
	public Section[] sections = null;
}
