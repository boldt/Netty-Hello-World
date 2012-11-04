package de.dennis_boldt.netty;

/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.devsprint.jersey.api.container.netty.NettyServer;
import com.devsprint.jersey.api.container.netty.NettyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class RESTServer {

	private static final String host = "localhost";
	private static final String RESOURCES_PACKAGE = "de.dennis_boldt.netty.JerseyServer.buisinesslogic";

	private static NettyServer server;

	protected static void startServer(final ResourceConfig resourceConfig,
			final URI baseUri) {
		server = NettyServerFactory.create(resourceConfig, baseUri);
		server.startServer();
	}

	protected static void stopServer() {
		server.stopServer();
	}

	/**
	 * Create an instance of <code>JerseyHandler</code>, base on class-path
	 * scanning.
	 * 
	 * @param baseUri
	 *            - base uri
	 * @return JerseyHandler instance.
	 */
	private static ResourceConfig getResourceConfiguration(final String baseUri) {
		final Map<String, Object> props = new HashMap<String, Object>();
		props.put(PackagesResourceConfig.PROPERTY_PACKAGES, RESOURCES_PACKAGE);
		props.put(NettyServer.PROPERTY_BASE_URI, baseUri);
		return new PackagesResourceConfig(props);

	}

	public static void main(String[] args) {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 9999;
		}

		// create server configuration

		final StringBuilder baseUri = new StringBuilder("http://");
		baseUri.append(host).append(":").append(String.valueOf(port))
				.append("/");
		final ResourceConfig resourceConfig = getResourceConfiguration(baseUri
				.toString());

		// start server
		startServer(resourceConfig, URI.create(baseUri.toString()));

		// add hook to stop server
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				stopServer();
			}

		});
	}
}
