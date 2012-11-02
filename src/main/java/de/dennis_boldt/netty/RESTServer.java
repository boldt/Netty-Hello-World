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

public class RESTServer {

    private int port = 0;
    private final String host = "localhost";
    private final String resources = "de.dennis_boldt.netty.JerseyServer.buisinesslogic";
    private StringBuilder baseUri = null;

    public RESTServer(int port) {
        this.port = port;
    }

    public void start() {
        // Configure the server
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                ));


        JaxRsServerChannelPipelineFactory pipeline = new JaxRsServerChannelPipelineFactory(getJerseyHandler());
        bootstrap.setPipelineFactory(pipeline);

        // Bind and start to accept incoming connections
        bootstrap.bind(new InetSocketAddress(port));

        System.out.println("HTTP server started at " + baseUri);
    }

    private JerseyHandler getJerseyHandler() {
        // Generate base URI
        baseUri = new StringBuilder("http://");
        baseUri.append(host).append(":").append(String.valueOf(port)).append("/");

        // Jersey config
        final Map<String, Object> inti_params = new HashMap<String, Object>();
        inti_params.put("com.sun.jersey.config.property.packages", resources);
        inti_params.put("com.devsprint.jersey.api.container.netty.baseUri", baseUri);
        inti_params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        ResourceConfig resourceConfig = new PackagesResourceConfig(inti_params);

        // Generate the Jersey handler
        return ContainerFactory.createContainer(JerseyHandler.class, resourceConfig);
    }


    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 9999;
        }
        RESTServer server = new RESTServer(port);
        server.start();
    }
}
