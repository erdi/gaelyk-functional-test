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

package groovyx.gaelyk.functional.datastore

import com.google.appengine.api.datastore.Entity

class DataBuilder {
	void build(Closure builder) {
		builder.setDelegate(this)
		builder.call()
	}

	def methodMissing(String name, args) {
		if (validateArgs(args)) {
			args = args as Stack
			def configureClosure = args.pop()
			def entity = new Entity(name, *args)
			entity.with(configureClosure)
			entity.save()
			entity
		} else {
			throw new MissingMethodException(name, getClass(), args)
		}
	}

	private boolean validateArgs(args) {
		switch (args.size()) {
			case 1:
				args[0] in Closure
				break
			case 2:
				args[1] in Closure && args[0].class in [String, Integer, Long]
				break
			default:
				false
		}
	}
}
