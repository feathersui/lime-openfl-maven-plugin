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
 * Represents the {@code <app>} element of the Lime <em>project.xml</em> format.
 * 
 * <p>
 * The following example sets the {@link #main} and {@link #file}
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
 * @see <a href="https://lime.software/docs/project-files/xml-format/#app">Lime
 *      <em>project.xml</em> format: &lt;app&gt;</a>
 */
public class App {
	/**
	 * The main entry point of the application. Represents the
	 * {@code <app main="">} attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <main>} element is not defined, the
	 * default value will be `Main`.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <main>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <main>com.example.MyApp</main>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String main = null;

	/**
	 * The name of the output file. Represents the {@code <app file="">}
	 * attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <file>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <file>myapp</file>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String file = null;

	/**
	 * Optionally sets a class to use as a custom preloader. Represents the
	 * {@code <app preloader="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <preloader>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <preloader>com.example.MyPreloader</preloader>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String preloader = null;

	/**
	 * Optionally sets the SWF version for `flash` and `air` targets. Represents
	 * the {@code <app swf-version="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <swf-version>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <swf-version>11</swf-version>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer swfVersion = null;

	/**
	 * Optionally sets the minimum SWF version for `flash` and `air` targets.
	 * Represents the {@code <app min-swf-version="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <min-swf-version>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <app>
	 *     <min-swf-version>11</min-swf-version>
	 *   </app>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer minSwfVersion = null;
}
