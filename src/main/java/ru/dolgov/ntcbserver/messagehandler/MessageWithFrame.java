package ru.dolgov.ntcbserver.messagehandler;

public class MessageWithFrame extends Message {
    @Override
    public void fromByteArray(byte[] bytes) {

    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
