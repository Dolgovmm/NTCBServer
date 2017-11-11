package ru.dolgov.ntcbserver.messagehandler;

public interface MessageHandler {

    byte[] checkMessage(byte[] bytes);
}
