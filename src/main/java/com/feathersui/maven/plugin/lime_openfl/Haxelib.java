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

/**
 * Represents the {@code <haxelib>} element of the Lime <em>project.xml</em>
 * format.
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
 *   </haxelibs>
 * </limeProject>
 * }
 * </pre>
 * 
 * @see <a href=
 *      "https://lime.software/docs/project-files/xml-format/#haxelib">Lime
 *      <em>project.xml</em> format: &lt;haxelib&gt;</a>
 */
public class Haxelib {
	/**
	 * The library's name.
	 * 
	 * <p>
	 * The following example sets a custom {@code <name>} element explicitly.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>
	 *         <name>openfl</name>
	 *     </haxelib>
	 *     <haxelib>
	 *         <name>actuate</name>
	 *     </haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * <p>
	 * The {@code <haxelibs>} element supports using a simple {@code String}
	 * value as shorthand, if no {@link #version} value needs to be specified.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>openfl</haxelib>
	 *     <haxelib>actuate</haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String name;

	/**
	 * Optionally specify the library's version.
	 * 
	 * <p>
	 * The following example sets {@code <version>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>
	 *         <name>openfl</name>
	 *         <version>9.2.0</version>
	 *     </haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String version = null;

	/**
	 * Optionally specify if the library is optional.
	 * 
	 * <p>
	 * The following example sets {@code <optional>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>
	 *         <name>openfl</name>
	 *         <optional>true</optional>
	 *     </haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean optional = null;

	/**
	 * Optionally specify a custom path to the library.
	 * 
	 * <p>
	 * The following example sets {@code <path>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <haxelibs>
	 *     <haxelib>
	 *         <name>openfl</name>
	 *         <path>/foo/bar/baz</path>
	 *     </haxelib>
	 *   </haxelibs>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public File path = null;

	/**
	 * Allows the Haxelib to be created from a simple {@code String} that
	 * represents its name.
	 * 
	 * @see #name
	 */
	public void set(String name) {
		this.name = name;
	}
}
