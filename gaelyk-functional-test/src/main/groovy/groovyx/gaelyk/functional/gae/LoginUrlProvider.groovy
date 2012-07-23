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

package groovyx.gaelyk.functional.gae

import groovyx.gaelyk.functional.remote.RemoteControl
import geb.Page

class LoginUrlProvider {
	private final static String UTF_8 = 'UTF-8'
	private String toQueryString(Map params) {
		if (params) {
			params.collect { name, value ->
				def values = value instanceof Collection ? value : [value]
				values.collect { v ->
					"${URLEncoder.encode(name.toString(), UTF_8)}=${URLEncoder.encode(v.toString(), UTF_8)}"
				}
			}.flatten().join('&')
		} else {
			''
		}
	}

	String getUrl(String baseUrl, Page page, Map params, Object[] args) {
		URI uri
		if (page.url) {
			uri = new URI(page.url)
			if (!uri.absolute) {
				uri = new URI(baseUrl).resolve(uri)
			}
		} else {
			uri = new URI(baseUrl)
		}

		String path = page.convertToPath(args)
		if (path) {
			uri = new URL(uri.toString() + path).toURI()
		}

		def queryString = toQueryString(params)
		if (queryString) {
			def joiner = uri.query ? '&' : '?'
			uri = new URL(uri.toString() + joiner + queryString).toURI()
		}

		new RemoteControl().exec { users.createLoginURL(uri.toString()) }
	}
}
