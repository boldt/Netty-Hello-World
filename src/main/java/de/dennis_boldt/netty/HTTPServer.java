package de.dennis_boldt.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import de.dennis_boldt.HttpServer.HttpServerPipelineFactory;

public class HTTPServer {

    private int port = 0;

    public HTTPServer(int port) {
        this.port = port;
    }

    public void start() {
        // Configure the server
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()
                ));

        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
        bootstrap.bind(new InetSocketAddress(port));

        System.out.println("HTTP server started on port " + port);
    }


    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8888;
        }
        HTTPServer server = new HTTPServer(port);
        server.start();
    }
}
