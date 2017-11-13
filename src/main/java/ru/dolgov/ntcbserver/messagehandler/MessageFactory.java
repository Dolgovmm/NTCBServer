package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

public class MessageFactory {

    public static Message getMessage(byte[] bytes) {
        String msg = new String(Message.getCharArray(Arrays.copyOfRange(bytes, 16, bytes.length)));
        if (msg.contains("*>S")) {
            return new MessageToConnect();
        }
        if (msg.contains("*>FLEX")) {
            return new MessageToSetSetting();
        }
        return new MessageWithFrame();
    }
}
