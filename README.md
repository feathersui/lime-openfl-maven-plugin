# Lime & OpenFL Maven Plugin

A plugin to build [Lime](https://lime.software/), [OpenFL](https://openfl.org/), and [Feathers UI](https://feathersui.com/) projects with [Apache Maven](https://maven.apache.org/).

Performs the following actions:

- Either detects your existing [Lime _project.xml_ file](https://lime.software/docs/project-files/xml-format/) or generates one from the [`limeProject` parameter](https://feathersui.github.io/lime-openfl-maven-plugin/apidocs/com/feathersui/maven/plugin/lime_openfl/LimeProject.html) configured in your Maven _pom.xml_ file.
- Installs any Haxelib libraries specified in _project.xml_ or _pom.xml_.
- Executes the `lime build` command for the specified target to build your application.
- Detects [utest](https://lib.haxe.org/p/utest/) test cases and generates a runner.
- Executes the test runner.

## Prerequisites

- The [Haxe](https://haxe.org/download/) compiler must be installed.

## Usage

The _lime-openfl-maven-plugin_ includes a couple of goals for building Lime projects.

### Build a Lime project

The following sample Maven _pom.xml_ demonstrates how to build an existing project with the plugin. Your existing _project.xml_ file should be in the same directory as your _pom.xml_ file.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>example-lime-project</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>lime-openfl</packaging>

  <build>
    <plugins>
      <plugin>
        <groupId>com.feathersui.maven.plugins</groupId>
        <artifactId>lime-openfl-maven-plugin</artifactId>
        <version>1.0.0</version>
        <extensions>true</extensions>
      </plugin>
    </plugins>
  </build>
</project>
```

Run `mvn compile` in a terminal to build your project.

## Generate a Lime _project.xml_ file

If you don't have an existing Lime _project.xml_ file, or if you prefer to manage all build configuration in the Maven _pom.xml_ file, you can add a [`<limeProject>` parameter](https://feathersui.github.io/lime-openfl-maven-plugin/apidocs/com/feathersui/maven/plugin/lime_openfl/LimeProject.html) in the `configuration` section for the plugin.

```xml
<plugin>
  <groupId>com.feathersui.maven.plugins</groupId>
  <artifactId>lime-openfl-maven-plugin</artifactId>
  <version>1.0.0</version>
  <extensions>true</extensions>
  <configuration>
    <limeProject>
      <app>
        <main>com.example.MyApp</main>
        <file>myapp</file>
      </app>
      <window>
        <fps>60</fps>
        <allowHighDpi>true</allowHighDpi>
      </window>
      <haxelibs>
        <haxelib>openfl</haxelib>
        <haxelib>actuate</haxelib>
      </haxelibs>
      <icons>
        <icon>assets/myapp-icon.svg</icon>
      </icons>
    </limeProject>
  </configuration>
</plugin>
```

#### Configuring editors with generated project file

In Visual Studio Code, you can specify the `lime.projectFile` setting to use the generated project file.

```json
{
  "lime.projectFile": "target/project.xml"
}
```

For code intelligence to work properly, you must build the project at least once with Maven to ensure that this file exists.

## Build the plugin from source

Run the following command in this directory to build and install the lime-openfl-maven-plugin in your local repository.

```sh
mvn clean install
```
