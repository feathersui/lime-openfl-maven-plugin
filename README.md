# Lime & OpenFL Maven Plugin

A plugin to build [Lime](https://lime.software/), [OpenFL](https://openfl.org/), and [Feathers UI](https://feathersui.com/) projects with [Apache Maven](https://maven.apache.org/).

Performs the following actions:

- Can use your existing [Lime _project.xml_ file](https://lime.software/docs/project-files/xml-format/), or it can generate one from parameters in your Maven _pom.xml_ file.
- If necessary, installs any Haxelib libraries specified in _project.xml_.
- Executes the `lime build` command for the specified target.

## Prerequisites

- The [Haxe](https://haxe.org/download/) compiler must be installed.

## Installation

Currently, the plugin must be built and installed manually. See below.

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
  <packaging>pom</packaging>

  <build>
    <plugins>
      <plugin>
        <groupId>com.feathersui.maven.plugins</groupId>
        <artifactId>lime-openfl-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <executions>
          <execution>
            <goals>
              <goal>build</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

## Generate a Lime _project.xml_ file

If you don't have an existing Lime _project.xml_ file, or if you prefer to manage all build configuration in the Maven _pom.xml_ file, you can add the `generate-project-xml` goal too.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>example-lime-project</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <build>
    <plugins>
      <plugin>
        <groupId>com.feathersui.maven.plugins</groupId>
        <artifactId>lime-openfl-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <executions>
          <execution>
            <goals>
              <goal>generate-project-xml</goal>
              <goal>build</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
</project>
```

To customize the generated _project.xml_ file, add a `<limeProject>` parameter in the `configuration` section for the plugin.

```xml
<plugin>
  <groupId>com.feathersui.maven.plugins</groupId>
  <artifactId>lime-openfl-maven-plugin</artifactId>
  <version>1.0.0-SNAPSHOT</version>
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
      </haxelib>
      <icons>
        <icon>assets/myapp-icon.svg</icon>
      </icons>
    </limeProject>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>generate-project-xml</goal>
        <goal>build</goal>
      </goals>
    </execution>
  </executions>
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

Run the following command to build and install the plugin.

```sh
mvn clean install
```
