# Toolisticon Annotation Processor Maven Archetype

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.maven.archetypes/annotationprocessor-archetype/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.toolisticon.maven.archetypes/annotationprocessor-archetype)

This project allows you to setup Toolisticon flavoured and maven based annotation processor projects. 

# Why you should use this project?

Project setup can be easily created by using the maven archetype.
It will setup a working annotation processor project including

- api submodule that declares all annotations 
- Annotation processor submodule including unit tests examples
- example submodule


# How does it work?

You just need to create the project by using te following maven command

	mvn archetype:generate \
    	-DarchetypeGroupId=io.toolisticon.maven.archetypes \
    	-DarchetypeArtifactId=annotationprocessor-archetype \
    	-DarchetypeVersion=0.10.0 \
    	-DgroupId=<your processor projects group id> \
    	-DartifactId=<your processor projects artifact id> \
    	-Dversion=<your version number> \
    	-Dpackage=<your processor projects base package> \
    	-DannotationName=<the name of your annotation that should be processed>
    	


Pojo classes you want to create a builder for must be annotated with the Builder annotation.

One hint: some IDEs doesn't work properly with annotation processors.
This is usually caused by incremental builds or insufficient configuration.
A simple workaround is to build from command line inbetween, usually generated classes will be picked up automatically from the target folder afterwards.


# Contributing

We welcome any kind of suggestions and pull requests.

## Building and developing the annotation processor archetype

The archetype is built using Maven.
A simple import of the pom in your IDE should get you up and running. To build the archetype on the commandline, just run `mvn` or `mvn clean install`

## Requirements

The likelihood of a pull request being used rises with the following properties:

- You have used a feature branch.
- You have included a test that demonstrates the functionality added or fixed.
- You adhered to the [code conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html).

## Contributions

- (2019) Tobias Stamann (Holisticon AG)

# License

This project is released under the revised [MIT License](LICENSE).
