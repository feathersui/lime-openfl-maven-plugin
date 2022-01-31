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
 * Represents the {@code <window>} element of the Lime <em>project.xml</em>
 * format.
 *
 * <p>
 * The following example sets the {@link #fps} and
 * {@link #allowHighDpi} fields.
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
 * @see <a href=
 *      "https://lime.software/docs/project-files/xml-format/#window">Lime
 *      <em>project.xml</em> format: &lt;window&gt;</a>
 */
public class Window {
	/**
	 * The default width of the main window. Represents the
	 * {@code <window width="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <width>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <width>640</width>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer width = null;

	/**
	 * The default height of the main window. Represents the
	 * {@code <window height="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <height>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <height>480</height>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer height = null;

	/**
	 * The background color of the main window. Represents the
	 * {@code <window background="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <background>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <background>#CCCCCC</background>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String background = null;

	/**
	 * The frames per second. Represents the {@code <window fps="">} attribute
	 * of the Lime <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <fps>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <fps>60</fps>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer fps = null;

	/**
	 * Whether the main window defaults to full screen or windowed. Represents
	 * the {@code <window fullscreen="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <fullscreen>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <fullscreen>true</fullscreen>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean fullscreen = null;

	/**
	 * Whether the main window is resizable. Represents the
	 * {@code <window resizable="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <resizable>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <resizable>true</resizable>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean resizable = null;

	/**
	 * Whether the main window is borderless. Represents the
	 * {@code <window borderless="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <borderless>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <borderless>true</borderless>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean borderless = null;

	/**
	 * Whether vsync is enabled or not. Represents the {@code <window vsync="">}
	 * attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <vsync>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <vsync>true</vsync>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean vsync = null;

	/**
	 * The orientation of the application. Represents the
	 * {@code <window orientation="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <orientation>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <orientation>portrait</orientation>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String orientation = null;

	/**
	 * Whether hardware is allowed or not. Represents the
	 * {@code <window hardware="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <p>
	 * The following example sets the {@code <hardware>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <hardware>false</hardware>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean hardware = null;

	/**
	 * Whether shaders are allowed or not. Represents the
	 * {@code <window allow-shaders="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <allowShaders>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <allowShaders>false</allowShaders>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean allowShaders = null;

	/**
	 * Whether shaders are required or not. Represents the
	 * {@code <window require-shaders="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <requireShaders>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <requireShaders>false</requireShaders>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean requireShaders = null;

	/**
	 * Whether the depth buffer is enabled or not. Represents the
	 * {@code <window depth-buffer="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <depthBuffer>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <depthBuffer>false</depthBuffer>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean depthBuffer = null;

	/**
	 * Whether the stencil buffer is enabled or not. Represents the
	 * {@code <window stencil-buffer="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <stencilBuffer>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <stencilBuffer>false</stencilBuffer>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean stencilBuffer = null;

	/**
	 * The level of anti-aliasing. Represents the
	 * {@code <window antialiasing="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <antialiasing>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <antialiasing>1</antialiasing>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer antialiasing = null;

	/**
	 * Whether high DPI rendering is allowed or not. Represents the
	 * {@code <window allow-high-dpi="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <allowHighDpi>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <allowHighDpi>true</allowHighDpi>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Boolean allowHighDpi = null;

	/**
	 * Sets the color depth. Represents the {@code <window color-depth="">}
	 * attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <colorDepth>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <window>
	 *     <colorDepth>16</colorDepth>
	 *   </window>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public Integer colorDepth = null;
}
