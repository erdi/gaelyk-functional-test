/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package groovyx.gaelyk.functional.spock

import groovyx.gaelyk.functional.util.DatastoreStateAwareSpec
import spock.lang.Stepwise

@Stepwise
class ModifiesDatastoreOnFeatureSpec extends DatastoreStateAwareSpec {

	@ModifiesDatastore
	def 'a feature that modifies datastore and is annotated as such'() {
		when:
		modifyDatastore()

		then:
		datastoreModified
	}

	def 'feature after an annotated feature should see a clean datastore'() {
		expect:
		!datastoreModified
	}

	def 'a feature that modifies datastore and is not annotated as such'() {
		when:
		modifyDatastore()

		then:
		datastoreModified
	}

	def 'feature after a non annotated feature should still see the changes'() {
		expect:
		datastoreModified
	}
}
