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


import spock.lang.Specification
import groovyx.gaelyk.functional.datastore.DatastoreCleaner
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.MethodInfo
import spock.lang.Unroll
import java.lang.reflect.AnnotatedElement

class FeatureDatastoreCleaningInterceptorSpec extends Specification {
	@Unroll
	def 'proceed is being called after interception'() {
		given:
		DatastoreCleaner mockCleaner = Mock()
		IMethodInvocation mockInvocation = Mock()
		FeatureInfo mockFeature = Mock()
		MethodInfo mockFeatureMethod = Mock()
		AnnotatedElement mockReflection = Mock()

		when:
		new FeatureDatastoreCleaningInterceptor(mockCleaner).intercept(mockInvocation)

		then:
		1 * mockInvocation.feature >> mockFeature
		1 * mockFeature.featureMethod >> mockFeatureMethod
		1 * mockFeatureMethod.reflection >> mockReflection
		1 * mockReflection.isAnnotationPresent(ModifiesDatastore) >> annotationPresent
		cleanCalls * mockCleaner.clean()
		1 * mockInvocation.proceed()

		where:
		annotationPresent | cleanCalls
		true              | 1
		false             | 0
	}
}
