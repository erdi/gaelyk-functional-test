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

package groovyx.gaelyk.functional.util

import spock.lang.Specification
import groovyx.gaelyk.functional.remote.RemoteControl
import com.google.appengine.api.datastore.Entity

abstract class DatastoreStateAwareSpec extends Specification {
	protected boolean isDatastoreModified() {
		new RemoteControl().exec { datastore.execute { select count } }
	}

	protected void modifyDatastore() {
		new RemoteControl(useNullIfResultWasUnserializable: true).exec { new Entity('testEntity', 'testKey').save() }
	}
}
