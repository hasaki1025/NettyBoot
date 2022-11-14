package com.example.nettyboot;

import com.example.nettyboot.Server.DownloadServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyServerRunner implements ApplicationRunner {

    @Autowired
    DownloadServer downloadServer;
    @Value("${Netty.port}")
    int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("DownLoadServer started port : {}....",port);
        downloadServer.init(port);
    }
}
