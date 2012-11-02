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

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.devsprint.jersey.api.container.netty.JaxRsServerChannelPipelineFactory;
import com.devsprint.jersey.api.container.netty.JerseyHandler;
import com.sun.jersey.api.container.ContainerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Server {

    private int port = 8080;
    private final String host = "localhost";
    private final String resources = "de.dennis_boldt.netty";

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        // Configure the server
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                ));

        // Generate base URI
        final StringBuilder baseUri = new StringBuilder("http://");
        baseUri.append(host).append(":").append(String.valueOf(port))
                .append("/");

        // Jersey config
        final Map<String, Object> props = new HashMap<String, Object>();
        props.put("com.sun.jersey.config.property.packages", resources);
        props.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        props.put("com.devsprint.jersey.api.container.netty.baseUri", baseUri);
        ResourceConfig resourceConfig = new PackagesResourceConfig(props);

        // Generate the Jersey handler
        final JerseyHandler jerseyHandler = ContainerFactory.createContainer(JerseyHandler.class, resourceConfig);
        JaxRsServerChannelPipelineFactory pipeline = new JaxRsServerChannelPipelineFactory(jerseyHandler);
        bootstrap.setPipelineFactory(pipeline);

        // Bind and start to accept incoming connections
        bootstrap.bind(new InetSocketAddress(port));

        System.out.println("HTTP server started at " + baseUri);
    }

    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 10000;
        }
        Server server = new Server(port);
        server.start();
    }
}
