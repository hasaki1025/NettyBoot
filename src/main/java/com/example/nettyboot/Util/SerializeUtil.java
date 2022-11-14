package com.example.nettyboot.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerializeUtil<T> {

    Class<T> clazz;

    public T JDKSerial(byte[] msg)
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(msg));
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public T JSON(byte[] msg)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(msg, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
