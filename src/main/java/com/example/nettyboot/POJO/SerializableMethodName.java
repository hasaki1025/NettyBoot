package com.example.nettyboot.POJO;

import lombok.Data;


public enum SerializableMethodName {
    JDK(0),JSON(1);
    final int value;

    public int getValue() {
        return value;
    }

    SerializableMethodName(int i) {
        this.value=i;
    }
}
