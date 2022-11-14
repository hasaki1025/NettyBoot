package com.example.nettyboot;

import com.example.nettyboot.POJO.message.LoginRequestMessage;
import com.example.nettyboot.Protocol.MessageCodec;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class NettyBootApplicationTests {

    @Test
    void contextLoads() {

        EmbeddedChannel channel = new EmbeddedChannel(new LoggingHandler(LogLevel.INFO),new MessageCodec());
        LoginRequestMessage message = new LoginRequestMessage("张三","123");
        channel.writeOutbound(message);

    }

}
