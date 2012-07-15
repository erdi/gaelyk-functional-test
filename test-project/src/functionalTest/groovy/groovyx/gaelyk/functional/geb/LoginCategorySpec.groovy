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

import geb.spock.GebSpec
import groovyx.gaelyk.functional.geb.page.UserInfoPage
import groovyx.gaelyk.functional.geb.page.AnotherUserInfoPage
import spock.lang.Unroll

@Mixin(LoginCategory)
class LoginCategorySpec extends GebSpec {
	def 'user is not logged in'() {
		when:
		to UserInfoPage

		then:
		!loggedIn
	}

	@Unroll
	def '#scenario is logged in with username "#passedUsername" and lands at #destination.simpleName'() {
		when:
		loginTo(passedUsername, asAdmin, destination)

		then:
		page.getClass() == destination
		verifyAt()
		loggedIn
		isAdmin == asAdmin
		userName == passedUsername

		where:
		[asAdmin, destination, passedUsername] << [[true, false], [UserInfoPage, AnotherUserInfoPage], ['foo@example.com', 'bar@example.com']].combinations()
		scenario = asAdmin ? 'admin' : 'user'
	}
}
