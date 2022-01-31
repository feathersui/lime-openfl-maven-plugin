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
 * Represents the {@code <source>} element of the Lime <em>project.xml</em>
 * format.
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
 * @see <a href=
 *      "https://lime.software/docs/project-files/xml-format/#source">Lime
 *      <em>project.xml</em> format: &lt;source&gt;</a>
 */
public class Source {
	/**
	 * The path to the Haxe class path directory.
	 * 
	 * <p>
	 * The following example sets a custom {@code <path>} element explicitly.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <sources>
	 *     <source>
	 *       <path>foo/bar/baz</path>
	 *     </source>
	 *   </sources>
	 * </limeProject>
	 * }
	 * </pre>
	 * 
	 * <p>
	 * The {@code <sources>} element supports using a simple {@code String}
	 * value as shorthand.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <sources>
	 *     <source>foo/bar/baz</source>
	 *   </sources>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public File path;

	public void set(File path) {
		this.path = path;
	}

	public void set(String path) {
		this.path = new File(path);
	}
}
