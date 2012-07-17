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
import geb.Page

@Mixin(LoginCategory)
class LoginCategorySpec extends GebSpec {

	private void loggedInAt(Class<? extends Page> expectedPage) {
		assert page.getClass() == expectedPage
		assert verifyAt()
		assert loggedIn
	}

	private void loggedInAs(String passedUsername, boolean asAdmin) {
		assert userName == passedUsername
		assert isAdmin == asAdmin
	}

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
		loggedInAt(destination)
		loggedInAs(passedUsername, asAdmin)

		where:
		[asAdmin, destination, passedUsername] << [[true, false], [UserInfoPage, AnotherUserInfoPage], ['foo@example.com', 'bar@example.com']].combinations()
		scenario = asAdmin ? 'admin' : 'user'
	}

	@Unroll
	def 'parameters "#passedParams" are being passed to destination url when logging in as #scenario'() {
		when:
		loginTo(passedParams, 'foo@example.com', asAdmin, UserInfoPage)

		then:
		loggedInAt(UserInfoPage)
		loggedInAs('foo@example.com', asAdmin)
		params == passedParams

		where:
		[asAdmin, passedParams] << [[true, false], [[:], [a: 'b', c: '1234']]].combinations()
		scenario = asAdmin ? 'admin' : 'user'
	}

	@Unroll
	def 'additional call arguments are being added to url when logging in as #scenario with arguments #arguments'() {
		when:
		loginTo('foo@example.com', asAdmin, UserInfoPage, *arguments)

		then:
		loggedInAt(UserInfoPage)
		loggedInAs('foo@example.com', asAdmin)
		params.pathOne == (arguments.size() ? arguments[0] : null)
		params.pathTwo == (arguments.size() > 1 ? arguments[1] : null)

		where:
		[asAdmin, arguments] << [[true, false], [[], ['a'], ['b', 'c']]].combinations()
		scenario = asAdmin ? 'admin' : 'user'
	}

	@Unroll
	def '#scenario logs in with both arguments and params'() {
		when:
		loginTo('foo@example.com', asAdmin, UserInfoPage, 'a', x: 'y')

		then:
		loggedInAt(UserInfoPage)
		loggedInAs('foo@example.com', asAdmin)
		params.x == 'y'
		params.pathOne == 'a'

		where:
		asAdmin << [true, false]
		scenario = asAdmin ? 'admin' : 'user'
	}
}
