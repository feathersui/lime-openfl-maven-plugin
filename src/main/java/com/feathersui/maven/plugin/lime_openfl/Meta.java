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
 * Represents the {@code <meta>} element of the Lime <em>project.xml</em>
 * format.
 * 
 * <p>
 * The following example sets the {@link #title} and
 * {@link #description} fields.
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
 * @see <a href="https://lime.software/docs/project-files/xml-format/#meta">Lime
 *      <em>project.xml</em> format: &lt;meta&gt;</a>
 */
public class Meta {
	/**
	 * The title of the application. Represents the {@code <meta title="">}
	 * attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <title>} element is not defined, the
	 * default value will be populated from the project name in the Maven
	 * <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <title>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <title>My Application</title>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String title = null;

	/**
	 * The description of the application. Represents the
	 * {@code <meta description="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <description>} element is not
	 * defined, the default value will be populated from the project description
	 * in the Maven <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <description>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <description>Get more stuff done with My Cool App</description>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String description = null;

	/**
	 * The package identifier of the application. Represents the
	 * {@code <meta package="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <packageId>} element is not
	 * defined, the default value will be populated from the project description
	 * in the Maven <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <packageId>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <packageId>com.example.myapp</packageId>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String packageId = null;

	/**
	 * The version of the application. Represents the {@code <meta version="">}
	 * attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <version>} element is not
	 * defined, the default value will be populated from the project version
	 * in the Maven <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <version>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <version>1.2.3</version>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String version = null;

	/**
	 * The name of the company that created the application. Represents the
	 * {@code <meta company="">} attribute of the Lime <em>project.xml</em> format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <company>} element is not
	 * defined, the default value will be populated from the organization name
	 * in the Maven <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <company>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <company>Example, Inc.</company>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String company = null;

	/**
	 * Represents the {@code <meta company-id="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <companyId>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <companyId>id</companyId>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String companyId = null;

	/**
	 * The URL of the company that created the application. Represents the
	 * {@code <meta company-url="">} attribute of the Lime <em>project.xml</em>
	 * format.
	 * 
	 * <blockquote>
	 * <p>
	 * <strong>Hint:</strong> If the {@code <companyUrl>} element is not
	 * defined, the default value will be populated from the organization URL
	 * in the Maven <em>pom.xml</em> file.
	 * </p>
	 * </blockquote>
	 * 
	 * <p>
	 * The following example sets the {@code <companyUrl>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <companyUrl>https://example.com/</companyUrl>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String companyUrl = null;

	/**
	 * Represents the {@code <meta build-number="">} attribute of the Lime
	 * <em>project.xml</em> format.
	 * 
	 * <p>
	 * The following example sets the {@code <buildNumber>} element.
	 * </p>
	 * 
	 * <pre>
	 * {@code
	 * <limeProject>
	 *   <meta>
	 *     <buildNumber>5</buildNumber>
	 *   </meta>
	 * </limeProject>
	 * }
	 * </pre>
	 */
	public String buildNumber = null;
}
