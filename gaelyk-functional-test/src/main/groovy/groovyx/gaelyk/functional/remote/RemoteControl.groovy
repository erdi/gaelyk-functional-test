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

package groovyx.gaelyk.functional.remote

import groovyx.remote.transport.http.HttpTransport
import geb.ConfigurationLoader

class RemoteControl extends groovyx.remote.client.RemoteControl {
	static final String ENDPOINT_PROPERTY_NAME = 'gaelyk.remote.endpoint'

	RemoteControl() {
		super(new HttpTransport(determineRemoteControlServletUrl(), Thread.currentThread().contextClassLoader),
			Thread.currentThread().contextClassLoader)
	}

	private static String getRemoteControlServletEndpoint() {
		System.getProperty(ENDPOINT_PROPERTY_NAME) ?: 'remote-control'
	}

	private static String determineRemoteControlServletUrl() {
		def conf = new ConfigurationLoader().conf
		"${conf.baseUrl}$remoteControlServletEndpoint"
	}
}
