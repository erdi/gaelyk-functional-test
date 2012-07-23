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

package groovyx.gaelyk.functional.geb

import spock.lang.Specification
import groovyx.gaelyk.functional.remote.RemoteControl
import groovyx.gaelyk.functional.spock.ModifiesDatastore
import groovyx.remote.client.RemoteException
import spock.lang.Unroll

@Mixin(DataSetupCategory)
@ModifiesDatastore
class DataSetupCategorySpec extends Specification {
	def remote = new RemoteControl()

	def 'data is set up without a key'() {
		when:
		setupData {
			category {
				name = 'Groovy'
			}
		}

		then:
		categoryIsSetUp()
	}

	def 'data is set up with key name'() {
		when:
		setupData {
			category('keyname') {
				name = 'Groovy'
			}
		}

		then:
		categoryIsSetUp()
		remote.exec { datastore.execute { select single from category where name == 'Groovy' }.key.name } == 'keyname'
	}

	def 'data is set up with key id'() {
		when:
		setupData {
			category(1234) {
				name = 'Groovy'
			}
		}

		then:
		categoryIsSetUp()
		remote.exec { datastore.execute { select single from category where name == 'Groovy' }.key.id } == 1234
	}

	def 'setup calls save and return configured entity'() {
		when:
		setupData {
			def groovyCategory = category {
				name = 'Groovy'
			}
			post {
				category = groovyCategory.name
				title = 'Test post'
			}
		}

		then:
		categoryIsSetUp()
		remote.exec { datastore.execute { select count from post} } == 1
		remote.exec { datastore.execute { select single from post}.category } == 'Groovy'
		remote.exec { datastore.execute { select single from post}.title } == 'Test post'
	}

	@Unroll
	def 'invalid builder call - #scenario'() {
		when:
		setupData call

		then:
		RemoteException re = thrown()
		MissingMethodException mme = re.cause
		mme.method == 'category'
		mme.arguments == args

		where:
		scenario                      | args      | call
		'not enough args'             | []        | { category() }
		'too many args'               | [1, 2, 3] | { category 1, 2, 3 }
		'wrong type one arg'          | [1]       | { category 1 }
		'wrong type two args'         | [1, 2]    | { category 1, 2 }
	}

	private void categoryIsSetUp() {
		remote.exec { datastore.execute { select count from category} } == 1
		remote.exec { datastore.execute { select single from category}.name } == 'Groovy'
	}
}
