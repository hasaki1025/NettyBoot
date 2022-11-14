package com.example.nettyboot.Protocol;

import com.example.nettyboot.POJO.SerializableMethodName;
import com.example.nettyboot.POJO.message.Message;
import com.example.nettyboot.Util.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf buf) throws Exception {
        //写入魔数(4字节)
        buf.writeBytes(new byte[]{1,0,2,6});
        //写入版本号（2字节）
        buf.writeByte(1);
        buf.writeByte(0);
        //写入序列化算法指定(1字节)
        buf.writeByte(SerializableMethodName.JDK.getValue());
        //指令类型（1字节）
        buf.writeByte(message.getMessageType());
        //请求序号(4)
        buf.writeInt(1);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
        byte[] bytes = os.toByteArray();
        //正文长度(4)
        buf.writeInt(bytes.length);
        //消息本体(前面共16)
        //样式：
        // 01 00 02 06
        // 01 00
        // 00
        // 00
        // 00 00 00 01
        // 00 00 00 e6
        buf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        //魔数
        int mn = buf.readInt();
        log.debug("magic number :{}",mn);
        //版本
        byte version = buf.readByte();
        buf.readByte();
        log.debug("version number :{}",version);
        //序列化算法指定
        byte serName = buf.readByte();
        log.debug("serName  :{}",serName);
        //指令类型
        byte msgType = buf.readByte();
        log.debug("msgType  :{}",msgType);
        //请求序号
        int seq = buf.readInt();
        log.debug("seq  :{}",seq);
        //正文长度
        int len = buf.readInt();
        log.debug("len  :{}",len);
        //正文
        byte[] msg = new byte[len];
        buf.readBytes(msg);
        if (serName== SerializableMethodName.JDK.getValue()) {
            Message message = new SerializeUtil<Message>().JDKSerial(msg);
            log.info("Message : {}",message);
            list.add(message);
        }
        else {
            Message message = new SerializeUtil<Message>().JSON(msg);
            log.info("Message : {}",message);
            list.add(message);
        }
    }
}
