package com.example.nettyboot.Handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

@Component
@Scope("prototype")
@Slf4j
@ChannelHandler.Sharable
public class DownloaderHandler extends SimpleChannelInboundHandler<HttpRequest> {
    @Value("${Netty.sourcePath}")
    String sourcePath;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
        String uri = httpRequest.uri();
        log.info("Request URI: {}",uri);
        File file = new File(sourcePath + uri);
        if (file.exists())
        {
            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
            ByteBuf buffer = ctx.alloc().buffer();
            FileChannel channel = new FileInputStream(file).getChannel();
            buffer.writeBytes(channel,0,(int)channel.size());
            response.content().writeBytes(buffer.toString(StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8));
            response.headers().add(HttpHeaderNames.CONTENT_TYPE,"application/octet-stream");
            response.headers().add(HttpHeaderNames.ACCEPT_RANGES,"bytes");
            response.headers().add(HttpHeaderNames.CONTENT_LENGTH,channel.size());
            ctx.writeAndFlush(response);
        }
    }
}
