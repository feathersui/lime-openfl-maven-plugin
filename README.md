# Lime & OpenFL Maven Plugin

A plugin to build [Lime](https://lime.software/) and [OpenFL](https://openfl.org/) projects with [Apache Maven](https://maven.apache.org/).

Performs the following actions:

- If necessary, installs any Haxelib libraries specified in _project.xml_
- Executes the `lime build` command for the specified target

## Installation

Currently, the plugin must be built and installed manually. See below.

## Requirements

- The [Haxe](https://haxe.org/download/) compiler must be installed.
- A standard [Lime _project.xml_ file](https://lime.software/docs/project-files/xml-format/).

# Sample _pom.xml_

The following sample Maven _pom.xml_ demonstrates how to use the plugin.

```xml
<?xml version='1.0' encoding='UTF-8'?>
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

## Build the plugin from source

Run the following command to build and install the plugin.

```sh
mvn clean install
```
