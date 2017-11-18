package ru.dolgov.ntcbserver.messagehandler;

import java.nio.ByteBuffer;

public abstract class Message {
    protected String preambula;
    protected int idObj;
    protected int idDc;
    protected int size;
    protected int csd;
    protected int csp;
    protected long imei;

    public abstract void fromByteArray(byte[] bytes);

    public abstract byte[] toByteArray();

    public abstract String toLog(byte[] bytes);

    protected String getPreambula(byte[] bytes) {
        return new String(getCharArray(bytes));
    }

    protected int getIntFromBytes(byte[] bytes) {
        int result = bytes[0] & 0xFF;
        for (int i = bytes.length - 1; i > 0; i--) {
            result |= ((bytes[i] & 0xFF) << (i * 8));
        }
        return result;
    }

    protected long getLongFromBytes(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }

    protected byte[] getBytesFromInt(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((value >> 24) & 0xFF);
        bytes[1] = (byte) ((value >> 16) & 0xFF);
        bytes[2] = (byte) ((value >> 8) & 0xFF);
        bytes[3] = (byte) (value & 0xFF);
        return bytes;
    }

    protected byte getCRC(byte[] bytes) {
        int summ = 0;
        for (int i = 0; i < bytes.length; i++) {
            summ ^= bytes[i];
        }
        return (byte)(summ & 0xFF);
    }

    protected long getImei(byte[] bytes) {
        String str = new String(getCharArray(bytes));
        return Long.parseLong(str);
    }

    public static char[] getCharArray(byte[] bytes) {
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
