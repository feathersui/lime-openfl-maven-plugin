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
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Organization;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Generates a _project.xml_ file for a Lime or OpenFL project.
 */
@Mojo(name = "generate-project-xml", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GenerateProjectXmlMojo extends AbstractMojo {
	@Parameter
	private LimeProject limeProject;

	/**
	 * Optionally specify a custom path to the Lime project file.
	 */
	@Parameter(property = "lime.projectFile")
	private File projectFile;

	@Parameter(defaultValue = "${project}", required = true, readonly = true)
	private MavenProject project;

	/**
	 * The target directory of the project.xml file.
	 */
	@Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
	private File buildDirectory;

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (projectFile != null) {
			throw new MojoFailureException("Cannot set projectFile parameter with generate-project-xml goal");
		}

		try {
			String contents = generateProjectXML();
			File projectFile = new File(buildDirectory, "project.xml");
			FileUtils.write(projectFile, contents, Charset.forName("utf-8"));
			project.getProperties().setProperty("lime.projectFile", projectFile.getAbsolutePath());
		} catch (Exception e) {
			throw new MojoExecutionException("Fatal error generating Lime project file", e);
		}
	}

	private String generateProjectXML() throws MojoFailureException {
		StringBuilder builder = new StringBuilder()
				.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
				.append("<project>\n");

		// populate some meta values from the pom, if necessary
		if (limeProject == null || limeProject.meta == null || limeProject.meta.title == null) {
			builder.append("<meta title=\"")
					.append(project.getName())
					.append("\"/>\n");
		}
		if (limeProject == null || limeProject.meta == null || limeProject.meta.description == null) {
			String description = project.getDescription();
			if (description != null) {
				builder.append("<meta description=\"")
						.append(project.getDescription())
						.append("\"/>\n");
			}
		}
		if (limeProject == null || limeProject.meta == null || limeProject.meta.packageId == null) {
			builder.append("<meta package=\"")
					.append(project.getGroupId())
					.append(".")
					.append(project.getArtifactId())
					.append("\"/>\n");
		}
		if (limeProject == null || limeProject.meta == null || limeProject.meta.version == null) {
			builder.append("<meta version=\"")
					.append(project.getVersion())
					.append("\"/>\n");
		}
		if (limeProject == null || limeProject.meta == null || limeProject.meta.company == null) {
			Organization organization = project.getOrganization();
			if (organization != null && organization.getName() != null) {
				builder.append("<meta company=\"")
						.append(organization.getName())
						.append("\"/>\n");
			}
		}
		if (limeProject == null || limeProject.meta == null || limeProject.meta.companyUrl == null) {
			Organization organization = project.getOrganization();
			if (organization != null && organization.getUrl() != null) {
				builder.append("<meta company-url=\"")
						.append(organization.getUrl())
						.append("\"/>\n");
			}
		}
		// set a default source path, if necessary
		File defaultSourcePath = new File("src/main/haxe");
		if (limeProject == null || limeProject.sources == null
				|| Arrays.asList(limeProject.sources).stream()
						.noneMatch(
								source -> defaultSourcePath.getAbsolutePath().equals(source.path.getAbsolutePath()))) {
			builder.append("<source path=\"")
					.append(defaultSourcePath.getAbsolutePath())
					.append("\"/>\n");
		}
		// make sure that either the lime or openfl haxelib is included
		if (limeProject == null || limeProject.haxelibs == null || Arrays.asList(limeProject.haxelibs).stream()
				.noneMatch(haxelib -> "lime".equals(haxelib.name) || "openfl".equals(haxelib.name))) {
			builder.append("<haxelib name=\"lime\"/>\n");
		}

		// add everything else
		generateSectionXMLInternal(limeProject, builder);

		builder.append("</project>\n");
		return builder.toString();
	}

	private void generateSectionXML(Section section, StringBuilder builder) throws MojoFailureException {
		builder.append("<section");
		if (section.ifCondition != null) {
			builder.append(" if=\"" + section.ifCondition + "\"");
		}
		if (section.unlessCondition != null) {
			builder.append(" unless=\"" + section.unlessCondition + "\"");
		}
		builder.append(">\n");
		generateSectionXMLInternal(section, builder);
		builder.append("</section>\n");
	}

	private void generateSectionXMLInternal(LimeProject section, StringBuilder builder) throws MojoFailureException {
		if (section != null && section.app != null) {
			App app = section.app;
			if (app.main != null) {
				builder.append("<app main=\"")
						.append(app.main)
						.append("\"/>\n");
			}
			if (app.file != null) {
				builder.append("<app file=\"")
						.append(app.file)
						.append("\"/>\n");
			}
			if (app.preloader != null) {
				builder.append("<app preloader=\"")
						.append(app.preloader)
						.append("\"/>\n");
			}
			if (app.swfVersion != null) {
				builder.append("<app swf-version=\"")
						.append(app.swfVersion)
						.append("\"/>\n");
			}
			if (app.minSwfVersion != null) {
				builder.append("<app min-swf-version=\"")
						.append(app.minSwfVersion)
						.append("\"/>\n");
			}
		}
		if (section != null && section.meta != null) {
			Meta meta = section.meta;
			if (meta.title != null) {
				builder.append("<meta title=\"")
						.append(meta.title)
						.append("\"/>\n");
			}
			if (meta.description != null) {
				builder.append("<meta description=\"")
						.append(meta.description)
						.append("\"/>\n");
			}
			if (meta.packageId != null) {
				builder.append("<meta package=\"")
						.append(meta.packageId)
						.append("\"/>\n");
			}
			if (meta.version != null) {
				builder.append("<meta version=\"")
						.append(meta.version)
						.append("\"/>\n");
			}
			if (meta.company != null) {
				builder.append("<meta company=\"")
						.append(meta.company)
						.append("\"/>\n");
			}
			if (meta.companyId != null) {
				builder.append("<meta company-id=\"")
						.append(meta.companyId)
						.append("\"/>\n");
			}
			if (meta.companyUrl != null) {
				builder.append("<meta company-url=\"")
						.append(meta.companyUrl)
						.append("\"/>\n");
			}
			if (meta.buildNumber != null) {
				builder.append("<meta build-number=\"")
						.append(meta.buildNumber)
						.append("\"/>\n");
			}
		}
		if (section != null && section.window != null) {
			Window window = section.window;
			if (window.width != null) {
				builder.append("<window width=\"")
						.append(window.width)
						.append("\"/>\n");
			}
			if (window.height != null) {
				builder.append("<window height=\"")
						.append(window.height)
						.append("\"/>\n");
			}
			if (window.background != null) {
				builder.append("<window background=\"")
						.append(window.background)
						.append("\"/>\n");
			}
			if (window.fps != null) {
				builder.append("<window fps=\"")
						.append(window.fps)
						.append("\"/>\n");
			}
			if (window.fullscreen != null) {
				builder.append("<window fullscreen=\"")
						.append(window.fullscreen)
						.append("\"/>\n");
			}
			if (window.resizable != null) {
				builder.append("<window resizable=\"")
						.append(window.resizable)
						.append("\"/>\n");
			}
			if (window.borderless != null) {
				builder.append("<window borderless=\"")
						.append(window.borderless)
						.append("\"/>\n");
			}
			if (window.vsync != null) {
				builder.append("<window vsync=\"")
						.append(window.vsync)
						.append("\"/>\n");
			}
			if (window.orientation != null) {
				builder.append("<window orientation=\"")
						.append(window.orientation)
						.append("\"/>\n");
			}
			if (window.hardware != null) {
				builder.append("<window hardware=\"")
						.append(window.hardware)
						.append("\"/>\n");
			}
			if (window.allowShaders != null) {
				builder.append("<window allow-shaders=\"")
						.append(window.allowShaders)
						.append("\"/>\n");
			}
			if (window.requireShaders != null) {
				builder.append("<window require-shaders=\"")
						.append(window.requireShaders)
						.append("\"/>\n");
			}
			if (window.depthBuffer != null) {
				builder.append("<window depth-buffer=\"")
						.append(window.depthBuffer)
						.append("\"/>\n");
			}
			if (window.stencilBuffer != null) {
				builder.append("<window stencil-buffer=\"")
						.append(window.stencilBuffer)
						.append("\"/>\n");
			}
			if (window.antialiasing != null) {
				builder.append("<window antialiasing=\"")
						.append(window.antialiasing)
						.append("\"/>\n");
			}
			if (window.colorDepth != null) {
				builder.append("<window color-depth=\"")
						.append(window.colorDepth)
						.append("\"/>\n");
			}
			if (window.allowHighDpi != null) {
				builder.append("<window allow-high-dpi=\"")
						.append(window.allowHighDpi)
						.append("\"/>\n");
			}
		}
		if (section != null && section.sources != null) {
			for (Source source : section.sources) {
				builder.append("<source path=\"")
						.append(source.path.getAbsolutePath())
						.append("\"/>\n");
			}
		}
		if (section != null && section.java != null) {
			for (JavaSource javaFile : section.java) {
				builder.append("<java path=\"")
						.append(javaFile.path.getAbsolutePath())
						.append("\"/>\n");
			}
		}
		if (section != null && section.haxelibs != null) {
			for (Haxelib haxelib : section.haxelibs) {
				if (haxelib.name == null) {
					throw new MojoFailureException("Haxelib must have a name");
				}
				builder.append("<haxelib name=\"")
						.append(haxelib.name)
						.append("\"");
				if (haxelib.version != null) {
					builder.append(" version=\"")
							.append(haxelib.version)
							.append("\"");
				}
				if (haxelib.optional != null) {
					builder.append(" optional=\"")
							.append(haxelib.optional)
							.append("\"");
				}
				if (haxelib.path != null) {
					builder.append(" path=\"")
							.append(haxelib.path.getAbsolutePath())
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.haxedefs != null) {
			for (HaxeDef haxedef : section.haxedefs) {
				builder.append("<haxedef name=\"")
						.append(haxedef.name)
						.append("\"");
				if (haxedef.value != null) {
					builder.append(" value=\"")
							.append(haxedef.value)
							.append("\"");
				}
				if (haxedef.remove != null) {
					builder.append(" remove=\"")
							.append(haxedef.remove)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.setenvs != null) {
			for (SetEnv setenv : section.setenvs) {
				builder.append("<setenv name=\"")
						.append(setenv.name)
						.append("\"/>\n");
			}
		}
		if (section != null && section.haxeflags != null) {
			for (HaxeFlag haxeflag : section.haxeflags) {
				builder.append("<haxeflag name=\"")
						.append(haxeflag.name)
						.append("\" value=\"")
						.append(haxeflag.value)
						.append("\"/>\n");
			}
		}
		if (section != null && section.languages != null) {
			for (Language language : section.languages) {
				builder.append("<language name=\"")
						.append(language.name)
						.append("\"/>\n");
			}
		}
		if (section != null && section.includes != null) {
			for (Include include : section.includes) {
				builder.append("<include");
				if (include.path != null) {
					builder.append(" path=\"")
							.append(include.path.getAbsolutePath())
							.append("\"");
				}
				if (include.haxelib != null) {
					builder.append(" haxelib=\"")
							.append(include.haxelib)
							.append("\"");
				}
				if (include.noerror != null) {
					builder.append(" noerror=\"")
							.append(include.noerror)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.dependencies != null) {
			for (Dependency dependency : section.dependencies) {
				builder.append("<dependency name=\"")
						.append(dependency.name)
						.append("\"");
				if (dependency.path != null) {
					builder.append(" path=\"")
							.append(dependency.path.getAbsolutePath())
							.append("\"");
				}
				if (dependency.embed != null) {
					builder.append(" embed=\"")
							.append(dependency.embed)
							.append("\"");
				}
				if (dependency.forceLoad != null) {
					builder.append(" force-load=\"")
							.append(dependency.forceLoad)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.icons != null) {
			for (Icon icon : section.icons) {
				builder.append("<icon path=\"")
						.append(icon.path.getAbsolutePath())
						.append("\"");
				if (icon.size != null) {
					builder.append(" size=\"")
							.append(icon.size)
							.append("\"");
				}
				if (icon.width != null) {
					builder.append(" width=\"")
							.append(icon.width)
							.append("\"");
				}
				if (icon.height != null) {
					builder.append(" height=\"")
							.append(icon.height)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.templates != null) {
			for (Template template : section.templates) {
				builder.append("<template path=\"")
						.append(template.path.getAbsolutePath())
						.append("\"");
				if (template.haxelib != null) {
					builder.append(" haxelib=\"")
							.append(template.haxelib)
							.append("\"");
				}
				if (template.rename != null) {
					builder.append(" value=\"")
							.append(template.rename)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.certificates != null) {
			for (Certificate certificate : section.certificates) {
				builder.append("<certificate path=\"")
						.append(certificate.path.getAbsolutePath())
						.append("\"");
				if (certificate.type != null) {
					builder.append(" type=\"")
							.append(certificate.type)
							.append("\"");
				}
				if (certificate.identity != null) {
					builder.append(" identity=\"")
							.append(certificate.identity)
							.append("\"");
				}
				if (certificate.password != null) {
					builder.append(" password=\"")
							.append(certificate.password)
							.append("\"");
				}
				if (certificate.alias != null) {
					builder.append(" alias=\"")
							.append(certificate.alias)
							.append("\"");
				}
				if (certificate.aliasPassword != null) {
					builder.append(" alias-password=\"")
							.append(certificate.aliasPassword)
							.append("\"");
				}
				if (certificate.teamId != null) {
					builder.append(" team-id=\"")
							.append(certificate.teamId)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.assets != null) {
			for (Assets assets : section.assets) {
				builder.append("<assets path=\"")
						.append(assets.path.getAbsolutePath())
						.append("\"");
				if (assets.include != null) {
					builder.append(" include=\"")
							.append(assets.include)
							.append("\"");
				}
				if (assets.exclude != null) {
					builder.append(" exclude=\"")
							.append(assets.exclude)
							.append("\"");
				}
				if (assets.rename != null) {
					builder.append(" rename=\"")
							.append(assets.rename)
							.append("\"");
				}
				if (assets.type != null) {
					builder.append(" type=\"")
							.append(assets.type)
							.append("\"");
				}
				if (assets.embed != null) {
					builder.append(" embed=\"")
							.append(assets.embed)
							.append("\"");
				}
				if (assets.library != null) {
					builder.append(" library=\"")
							.append(assets.library)
							.append("\"");
				}
				if (assets.glyphs != null) {
					builder.append(" glyphs=\"")
							.append(assets.glyphs)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.libraries != null) {
			for (Library library : section.libraries) {
				builder.append("<library");
				if (library.path != null) {
					builder.append(" path=\"")
							.append(library.path.getAbsolutePath())
							.append("\"");
				}
				if (library.name != null) {
					builder.append(" name=\"")
							.append(library.name)
							.append("\"");
				}
				if (library.id != null) {
					builder.append(" id=\"")
							.append(library.id)
							.append("\"");
				}
				if (library.type != null) {
					builder.append(" type=\"")
							.append(library.type)
							.append("\"");
				}
				if (library.prefix != null) {
					builder.append(" prefix=\"")
							.append(library.prefix)
							.append("\"");
				}
				if (library.preload != null) {
					builder.append(" preload=\"")
							.append(library.preload)
							.append("\"");
				}
				if (library.embed != null) {
					builder.append(" embed=\"")
							.append(library.embed)
							.append("\"");
				}
				if (library.generate != null) {
					builder.append(" generate=\"")
							.append(library.generate)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.launchImage != null) {
			LaunchImage launchImage = section.launchImage;
			builder.append("<launchImage path=\"")
					.append(launchImage.path.getAbsolutePath())
					.append("\"");
			if (launchImage.width != null) {
				builder.append(" width=\"")
						.append(launchImage.width)
						.append("\"");
			}
			if (launchImage.height != null) {
				builder.append(" height=\"")
						.append(launchImage.height)
						.append("\"");
			}
			builder.append("/>\n");
		}
		if (section != null && section.ndlls != null) {
			for (Ndll ndll : section.ndlls) {
				builder.append("<ndll name=\"")
						.append(ndll.name)
						.append("\"");
				if (ndll.haxelib != null) {
					builder.append(" haxelib=\"")
							.append(ndll.haxelib)
							.append("\"");
				}
				if (ndll.dir != null) {
					builder.append(" dir=\"")
							.append(ndll.dir.getAbsolutePath())
							.append("\"");
				}
				if (ndll.type != null) {
					builder.append(" type=\"")
							.append(ndll.type)
							.append("\"");
				}
				if (ndll.register != null) {
					builder.append(" register=\"")
							.append(ndll.register)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.architectures != null) {
			for (Architecture architecture : section.architectures) {
				builder.append("<architecture");
				if (architecture.name != null) {
					builder.append(" name=\"")
							.append(architecture.name)
							.append("\"");
				}
				if (architecture.exclude != null) {
					builder.append(" dir=\"")
							.append(architecture.exclude)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.paths != null) {
			for (SystemPath path : section.paths) {
				builder.append("<path value=\"")
						.append(path.value)
						.append("\"/>\n");
			}
		}
		if (section != null && section.samples != null) {
			for (Sample sample : section.samples) {
				builder.append("<sample path=\"")
						.append(sample.path.getAbsolutePath())
						.append("\"/>\n");
			}
		}
		if (section != null && section.prebuilds != null) {
			for (PreBuild prebuild : section.prebuilds) {
				builder.append("<prebuild");
				if (prebuild.haxe != null) {
					builder.append(" haxe=\"")
							.append(prebuild.haxe)
							.append("\"");
				}
				if (prebuild.open != null) {
					builder.append(" open=\"")
							.append(prebuild.open.getAbsolutePath())
							.append("\"");
				}
				if (prebuild.command != null) {
					builder.append(" command=\"")
							.append(prebuild.command)
							.append("\"");
				}
				if (prebuild.cmd != null) {
					builder.append(" cmd=\"")
							.append(prebuild.cmd)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.postbuilds != null) {
			for (PostBuild postbuild : section.postbuilds) {
				builder.append("<postbuild");
				if (postbuild.haxe != null) {
					builder.append(" haxe=\"")
							.append(postbuild.haxe)
							.append("\"");
				}
				if (postbuild.open != null) {
					builder.append(" open=\"")
							.append(postbuild.open.getAbsolutePath())
							.append("\"");
				}
				if (postbuild.command != null) {
					builder.append(" command=\"")
							.append(postbuild.command)
							.append("\"");
				}
				if (postbuild.cmd != null) {
					builder.append(" cmd=\"")
							.append(postbuild.cmd)
							.append("\"");
				}
				builder.append("/>\n");
			}
		}
		if (section != null && section.sections != null) {
			for (Section subSection : section.sections) {
				generateSectionXML(subSection, builder);
			}
		}
	}

}
