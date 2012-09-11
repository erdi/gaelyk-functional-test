# Introduction

Gaelyk Functional Test Tools is a set of helpers to make functional testing of [Gaelyk][gaelyk] applications easier. This is just some code that seemed common enough to be extracted as a separate library which got written while working on my [blog][blog] that is [written using Gaelyk][bloogaey-clone].

Currently the library provides:

* own `RemoteControl` class that determines the remote control url based on conventions so that you don't have to specify one all the time
* `@ModifiesDatastore` [Spock][spock] extension that allows to wipe out datastore after running certain features
* a simple DSL for setting up data in the datastore if your feature needs a certain data state to run
* `loginTo` method that allows to go to a `Page` after logging into the application using default GAE authentication

## Project setup

> This section assumes that you are using Gradle and gradle-gaelyk-plugin version 0.4 or newer to build your project.

### Dependency on gaelyk-functional-test

The first step to use the library is to add a dependency on it to `functionalTestCompile` configuration:

	dependencies {
		functionalTestCompile 'org.gaelyk:gaelyk-functional-test:@gftt-version@'
	}

### Setting up remote control endpoint

The library depends on a [gaelyk-remote][gaelyk-remote] endpoint to be exposed at a specific url. To configure that you have to first add a runtime dependency on gaelyk-remote:

	dependencies {
		runtime 'org.gaelyk:gaelyk-remote:@gaelyk-remote-version@'
	}

and also add a servlet mapping to your `web.xml` file:

	<servlet>
		<servlet-name>RemoteControlServlet</servlet-name>
		<servlet-class>groovyx.gaelyk.remote.RemoteControlServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>RemoteControlServlet</servlet-name>
		<url-pattern>/remote-control</url-pattern>
	</servlet-mapping>

> If you wish to expose gaelyk-remote servlet at different url you will need to [override the default remote control url][override-remote-url] for the custom `RemoteControl` class delivered by the library

The custom `RemoteControl` class uses Geb's `baseUrl` configuration property to determine the base url of the gaelyk-remote servlet. The easiest way to configure that property is to apply [gradle-gae-geb-plugin][gradle-gae-geb-plugin] to the project:

	buildscript {
		repositories {
			mavenCentral()
		}

		dependencies {
			classpath "org.gradle.api.plugins:gradle-gae-geb-plugin:@gae-geb-plugin-version@"
		}
	}

	apply plugin: 'gae-geb'

### Dependency on gaelyk-functional-test-datastore-builder

If you wish to use the DSL for setting up data in the datastore you also have to add a runtime dependency on datastore builder:

	dependencies {
		runtime 'org.gaelyk:gaelyk-functional-test-datastore-builder:@gftt-version@'
	}

## Dependencies

The library integrates Gaelyk with [gaelyk-remote][gaelyk-remote], [Spock][spock] and [Geb][geb]. Currently it brings the following versions of the aforementioned tools into dependencies:

* org.gaelyk:gaelyk-remote:@gaelyk-remote-version@
* org.spockframework:spock-core:@spock-version@
* org.codehaus.geb:geb-spock:@geb-version@

The library is simple enough to assume that newer versions of those dependencies should still work. Just simply specify them in your `build.gradle` and they will overwrite the ones specified in the library - by default [the newest version of the dependency is used by Gradle if there is a conflict][gradle-conflict-resolution].