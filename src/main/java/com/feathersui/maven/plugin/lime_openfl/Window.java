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
 * Represents the &lt;window&gt; element of the Lime project XML format.
 * 
 * @see https://lime.software/docs/project-files/xml-format/#window
 */
public class Window {
	public Integer width = null;
	public Integer height = null;
	public String background = null;
	public Integer fps = null;
	public Boolean fullscreen = null;
	public Boolean resizable = null;
	public Boolean borderless = null;
	public Boolean vsync = null;
	public String orientation = null;
	public Boolean hardware = null;
	public Boolean allowShaders = null;
	public Boolean requireShaders = null;
	public Boolean depthBuffer = null;
	public Boolean stencilBuffer = null;
	public Boolean antialiasing = null;
	public Boolean allowHighDpi = null;
	public Integer colorDepth = null;
}
