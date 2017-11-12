package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

public class MessageHandlerImpl implements MessageHandler {

    @Override
    public byte[] checkMessage(byte[] bytes) {
        System.out.println(Arrays.toString(bytes));
        byte[] response;
        if (bytes.length == 35) {
            response = checkSettings(bytes);
        } else {
            if (bytes.length == 32) {
                response = checkFrame(bytes);
            }
            else {
                response = new byte[1];
            }
        }
        return response;
    }

    private byte[] checkSettings(byte[] bytes) {
        Message message = new Message();
        message.setPreambula(getPreambula(Arrays.copyOfRange(bytes, 0, 4)));
        message.setIdDc(getId(Arrays.copyOfRange(bytes, 4, 8)));
        message.setIdObj(getId(Arrays.copyOfRange(bytes, 8, 12)));
        message.setSize(getId(Arrays.copyOfRange(bytes, 12, 14)));
        message.setCsd(getCRC(Arrays.copyOfRange(bytes, 16, 35)));
        message.setCsp(getCRC(Arrays.copyOfRange(bytes, 0, 15)));
        message.setImei(getImei(Arrays.copyOfRange(bytes, 20, 35)));
        System.out.println(message.toString());
        return createResponse(message);
    }

    private byte[] createResponse(Message message) {
        byte[] bytes = new byte[19];
        bytes[0] = 0x40;
        bytes[1] = 0x4e;
        bytes[2] = 0x54;
        bytes[3] = 0x43;
        byte[] idObj = setID(message.getIdObj());
        for (int i = 0; i < idObj.length; i++) {
            bytes[i + 4] = idObj[idObj.length - 1 - i];
        }
        byte[] iddc = setID(message.getIdDc());
        for (int i = 0; i < iddc.length; i++) {
            bytes[i + 8] = iddc[idObj.length - 1 - i];
        }
        bytes[12] = 0x03;
        bytes[13] = 0x00;
        bytes[16] = 0x2a;
        bytes[17] = 0x3c;
        bytes[18] = 0x53;
        bytes[14] = (byte)getCRC(Arrays.copyOfRange(bytes, 16, 19));
        bytes[15] = (byte)getCRC(Arrays.copyOfRange(bytes, 0, 15));
        return bytes;
    }

    private byte[] checkFrame(byte[] bytes) {
        byte[] response = new byte[1];
        return response;
    }

    private String getPreambula(byte[] bytes) {
        return new String(getCharArray(bytes));
    }

    private long getImei(byte[] bytes) {
        return Long.parseLong(new String(getCharArray(bytes)));
    }

    private int getId(byte[] bytes) {
        int id = bytes[0] & 0xFF;
        for (int i = bytes.length - 1; i > 0; i--) {
            id = id | ((bytes[i] & 0xFF) << (i * 8));
        }
        return id;
    }

    private byte[] setID(int value) {
//        byte[] bytes = new byte[size - 1];
//        bytes[size - 1] = (byte) (value & 0xFF);
//        for (int i = 1; i < size - 2; i++) {
//            bytes[size - 2 - i] = (byte) ((value >> i * 8) & 0xFF);
//        }
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((value >> 24) & 0xFF);
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
    }

    private char getCRC(byte[] bytes) {
        int summ = 0;
        for (int i = 0; i < bytes.length-1; i++) {
            summ ^= bytes[i];
        }
        return (char)(summ & 0xFF);
    }

    private char[] getCharArray(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char)bytes[i];
        }
        return chars;
    }

    public static byte crc8 (byte[] buffer)
    {
        byte crc = (byte) 0xFF;
        for (byte b : buffer) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                crc = (crc & 0x80) != 0 ? (byte) ((crc << 1) ^ 0x31) : (byte) (crc << 1);
            }
        }
        return crc;
    }
}
