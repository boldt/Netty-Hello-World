package de.dennis_boldt.HttpServer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

/**
 * Generates the demo HTML page which is served at http://localhost:8080/
 */
public final class HttpServerIndexPage {

    public static ChannelBuffer getContent() {
        return ChannelBuffers.copiedBuffer("<p>Hello World</p>", CharsetUtil.UTF_8);
    }

    private HttpServerIndexPage() {
        // Unused
    }
}
