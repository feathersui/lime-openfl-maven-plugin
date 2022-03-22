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
 * Defines a <a href="https://lime.software/">Lime</a>,
 * <a href="https://openfl.org/">OpenFL</a>, or
 * <a href="https://feathersui.com/">Feathers UI</a> project in your Maven
 * <em>pom.xml</em> file for {@link GenerateProjectXmlMojo}.
 * 
 * <p>
 * To define your project configuration, add a {@code <limeProject>} parameter
 * to the {@code <configuration>} section for <em>lime-openfl-maven-plugin</em>
 * in your Maven <em>pom.xml</em> file.
 * </p>
 * 
 * <pre>
 * {@code
 * <build>
 *   <plugins>
 *     <plugin>
 *       <groupId>com.feathersui.maven.plugins</groupId>
 *       <artifactId>lime-openfl-maven-plugin</artifactId>
 *       <version>1.0.0</version>
 *       <extensions>true</extensions>
 *       <configuration>
 *         <limeProject>
 *           <app>
 *             <main>com.example.MyApp</main>
 *             <file>myapp</file>
 *           </app>
 *           <window>
 *             <fps>60</fps>
 *             <allowHighDpi>true</allowHighDpi>
 *           </window>
 *           <haxelibs>
 *             <haxelib>openfl</haxelib>
 *             <haxelib>actuate</haxelib>
 *           </haxelib>
 *           <icons>
 *             <icon>assets/myapp-icon.svg</icon>
 *           </icons>
 *         </limeProject>
 *       </configuration>
 *     </plugin>
 *   </plugins>
 * </build>
 * }
 * </pre>
 * 
 * <p>
 * <strong>Warning:</strong> While the Lime <em>project.xml</em> file and the
 * Maven <em>pom.xml</em> file are both forms of XML, and they both represent
 * the same data, they don't have the exact same format, so you cannot simply
 * copy/paste between the two. See the examples in this documentation to learn
 * how to define a Lime project in your Maven <em>pom.xml</em> file. Links to
 * the Lime <em>project.xml</em> format are for reference purposes only.
 * </p>
 * 
 * @see <a href="https://lime.software/docs/project-files/xml-format/">Lime
 *      <em>project.xml</em> format</a>
 */
public class LimeProject {
	/**
	 * Represents the {@code <app>} element of the Lime <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@link App#main} and {@link App#file}
	 * fields.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <main>com.example.MyApp</main>
	 *     <file>myapp</file>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * @see App
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#app">Lime
	 *      <em>project.xml</em> format: &lt;app&gt;</a>
	 */
	public App app = null;

	/**
	 * Represents the {@code <meta>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@link Meta#title} and
	 * {@link Meta#description} fields.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <title>My Cool Application</title>
	 *     <description>Get more stuff done with My Cool App</description>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * @see Meta
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#meta">Lime
	 *      <em>project.xml</em> format: &lt;meta&gt;</a>
	 */
	public Meta meta = null;

	/**
	 * Represents the {@code <window>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@link Window#fps} and
	 * {@link Window#allowHighDpi} fields.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <fps>60</fps>
	 *     <allowHighDpi>true</allowHighDpi>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * @see Window
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#window">Lime
	 *      <em>project.xml</em> format: &lt;window&gt;</a>
	 */
	public Window window = null;

	/**
	 * Represents the {@code <source>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <sources>} element is not defined,
	 * <em>src/main/haxe</em> is added by default.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets a custom {@code <sources>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <sources>
	 *     <source>src/main/haxe</source>
	 *     <source>foo/bar/baz</source>
	 *   </sources>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * @see Source
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#source">Lime
	 *      <em>project.xml</em> format: &lt;source&gt;</a>
	 */
	public Source[] sources = null;

	/**
	 * Represents the {@code <haxelib>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <haxelibs>} element is not defined,
	 * or if <em>lime</em> or <em>openfl</em> is not included, <em>lime</em> is
	 * added by default.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets a custom {@code <haxelibs>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>
	 *       <name>openfl</name>
	 *       <version>9.2.0</version>
	 *     </haxelib>
	 *     <haxelib>actuate</haxelib>
	 *     <haxelib>feathersui</haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * @see Haxelib
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#haxelib">Lime
	 *      <em>project.xml</em> format: &lt;haxelib&gt;</a>
	 */
	public Haxelib[] haxelibs = null;

	/**
	 * Represents the {@code <haxedef>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see HaxeDef
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#haxedef">Lime
	 *      <em>project.xml</em> format: &lt;haxedef&gt;</a>
	 */
	public HaxeDef[] haxedefs = null;

	/**
	 * Represents the {@code <haxeflag>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see HaxeFlag
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#haxeflag">Lime
	 *      <em>project.xml</em> format: &lt;haxeflag&gt;</a>
	 */
	public HaxeFlag[] haxeflags = null;

	/**
	 * Represents the {@code <language>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Language
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#language">Lime
	 *      <em>project.xml</em> format: &lt;language&gt;</a>
	 */
	public Language[] languages = null;

	/**
	 * Represents the {@code <dependency>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Dependency
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#dependency">Lime
	 *      <em>project.xml</em> format: &lt;dependency&gt;</a>
	 */
	public Dependency[] dependencies = null;

	/**
	 * Represents the {@code <template>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Template
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#template">Lime
	 *      <em>project.xml</em> format: &lt;template&gt;</a>
	 */
	public Template[] templates = null;

	/**
	 * Represents the {@code <include>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Include
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#include">Lime
	 *      <em>project.xml</em> format: &lt;include&gt;</a>
	 */
	public Include[] includes = null;

	/**
	 * Represents the {@code <icon>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Icon
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#icon">Lime
	 *      <em>project.xml</em> format: &lt;icon&gt;</a>
	 */
	public Icon[] icons = null;

	/**
	 * Represents the {@code <assets>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Assets
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#assets">Lime
	 *      <em>project.xml</em> format: &lt;assets&gt;</a>
	 */
	public Assets[] assets = null;

	/**
	 * Represents the {@code <library>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Library
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#library">Lime
	 *      <em>project.xml</em> format: &lt;library&gt;</a>
	 */
	public Library[] libraries = null;

	/**
	 * Represents the {@code <java>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Java
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#java">Lime
	 *      <em>project.xml</em> format: &lt;java&gt;</a>
	 */
	public Java[] java = null;

	/**
	 * Represents the {@code <launchImage>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see LaunchImage
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#launchimage">Lime
	 *      <em>project.xml</em> format: &lt;launchimage&gt;</a>
	 */
	public LaunchImage launchImage = null;

	/**
	 * Represents the {@code <certificate>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Certificate
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#certificate">Lime
	 *      <em>project.xml</em> format: &lt;certificate&gt;</a>
	 */
	public Certificate[] certificates = null;

	/**
	 * Represents the {@code <ndll>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Ndll
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#ndll">Lime
	 *      <em>project.xml</em> format: &lt;ndll&gt;</a>
	 */
	public Ndll[] ndlls = null;

	/**
	 * Represents the {@code <path>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see SystemPath
	 * @see <a href="https://lime.software/docs/project-files/xml-format/#path">Lime
	 *      <em>project.xml</em> format: &lt;path&gt;</a>
	 */
	public SystemPath[] paths = null;

	/**
	 * Represents the {@code <sample>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Sample
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#sample">Lime
	 *      <em>project.xml</em> format: &lt;sample&gt;</a>
	 */
	public Sample[] samples = null;

	/**
	 * Represents the {@code <setenv>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see SetEnv
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#setenv">Lime
	 *      <em>project.xml</em> format: &lt;setenv&gt;</a>
	 */
	public SetEnv[] setenvs = null;

	/**
	 * Represents the {@code <architecture>} element of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * @see Architecture
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#architecture">Lime
	 *      <em>project.xml</em> format: &lt;architecture&gt;</a>
	 */
	public Architecture[] architectures = null;

	/**
	 * Represents the {@code <prebuild>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see PreBuild
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#prebuild">Lime
	 *      <em>project.xml</em> format: &lt;prebuild&gt;</a>
	 */
	public PreBuild[] prebuilds = null;

	/**
	 * Represents the {@code <postbuild>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see PostBuild
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#postbuild">Lime
	 *      <em>project.xml</em> format: &lt;postbuild&gt;</a>
	 */
	public PostBuild[] postbuilds = null;

	/**
	 * Represents the {@code <section>} element of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * @see Section
	 * @see <a href=
	 *      "https://lime.software/docs/project-files/xml-format/#section">Lime
	 *      <em>project.xml</em> format: &lt;section&gt;</a>
	 */
	public Section[] sections = null;
}
