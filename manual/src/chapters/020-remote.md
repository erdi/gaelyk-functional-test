# Using RemoteControl class to easily modify state of your remote application under test

The library contains a `groovyx.gaelyk.functional.remote.RemoteControl` class that makes it even simpler to modify the state of your remote server under test. The main aim of the class is to be able to communicate with the remote without the need of passing a remote control endpoint url when constructing the object. Please refer to the documentation of [gaelyk-remote][gaelyk-remote] to learn about [Gaelyk specific variables available in remote context][remote-context].

## Example

Given that you have properly [configured the remote control endpoint][remote-endpoint-configuration] the following is an example of checking that there currently are no entities in the datastore:

	import groovyx.gaelyk.functional.remote.RemoteControl
	import spock.lang.Specification

	class RemoteControlSpec extends Specification {
		def remote = new RemoteControl()

		void 'there are no entities in the datastore'() {
			expect:
			remote.exec { datastore.execute { select count } } == 0
		}
	}


## Internal usage

Please note that the class is used by other components of the library thus making proper [remote control endpoint configuration][remote-endpoint-configuration] essential for the library to work correctly.

## Overriding the default remote control url

By default `groovyx.gaelyk.functional.remote.RemoteControl` class expects the remote control endpoint to be located at `remote-control`. If for some reason you expose remote control servlet at a different endpoint you have to set `gaelyk.remote.endpoint` system property to contain your custom endpoint. To achieve that simply put the following in your gradle build file:

 	gaeFunctionalTest {
		systemProperty 'gaelyk.remote.endpoint', 'custom-remote-control'
	}