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
 * Represents the {@code <library>} element of the Lime <em>project.xml</em>
 * format.
 * 
 * @see <a href=
 *      "https://lime.software/docs/project-files/xml-format/#library">Lime
 *      <em>project.xml</em> format: &lt;library&gt;</a>
 */
public class Library {
	public String name = null;
	public String id = null;
	public String type = null;
	public File path = null;
	public Boolean preload = null;
	public Boolean embed = null;
	public Boolean generate = null;
	public String prefix = null;
}
