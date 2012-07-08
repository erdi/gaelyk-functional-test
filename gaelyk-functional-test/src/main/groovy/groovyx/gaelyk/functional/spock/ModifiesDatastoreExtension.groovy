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

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import groovyx.gaelyk.functional.datastore.DatastoreCleaner
import org.spockframework.runtime.model.SpecInfo
import org.spockframework.runtime.model.MethodInfo
import spock.lang.Stepwise

class ModifiesDatastoreExtension extends AbstractAnnotationDrivenExtension<ModifiesDatastore> {
	FeatureDatastoreCleaningInterceptor featureBasedInterceptor = new FeatureDatastoreCleaningInterceptor(new DatastoreCleaner())

	@Override
	void visitFeatureAnnotation(ModifiesDatastore annotation, FeatureInfo feature) {
		MethodInfo cleanupMethod = feature.parent.cleanupMethod
		if (!cleanupMethod.interceptors.contains(featureBasedInterceptor)) {
			feature.parent.cleanupMethod.addInterceptor(featureBasedInterceptor)
		}
	}

	@Override
	void visitSpecAnnotation(ModifiesDatastore annotation, SpecInfo spec) {
		MethodInfo intercepted = spec.reflection.isAnnotationPresent(Stepwise) ? spec.cleanupSpecMethod : spec.cleanupMethod
		intercepted.addInterceptor(new SpecDatastoreCleaningInterceptor(new DatastoreCleaner()))
	}
}
