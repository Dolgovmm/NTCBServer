package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

public class MessageToSetSetting extends Message {
    private int protocol;
    private int protocol_version;
    private int struct_version;
    private int data_size;
    private String bitField;

    @Override
    public void fromByteArray(byte[] bytes) {
        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 4));
        idDc = getIntFromBytes(Arrays.copyOfRange(bytes, 4, 8));
        idObj = getIntFromBytes(Arrays.copyOfRange(bytes, 8, 12));
        size = getIntFromBytes(Arrays.copyOfRange(bytes, 12, 14));
        csd = getCRC(Arrays.copyOfRange(bytes, 16, 35));
        csp = getCRC(Arrays.copyOfRange(bytes, 0, 15));
        imei = getImei(Arrays.copyOfRange(bytes, 20, 35));
        protocol = bytes[22];
        protocol_version = bytes[23];
        struct_version = bytes[24];
        data_size = bytes[25];
        int bits = Integer.parseInt(new String(Message.getCharArray(Arrays.copyOfRange(bytes, 26, 34))), 16);
        bitField = Integer.toBinaryString(bits);
        System.out.println(toString());
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[25];
        bytes[0] = 0x40;
        bytes[1] = 0x4e;
        bytes[2] = 0x54;
        bytes[3] = 0x43;
        byte[] tmp = getBytesFromInt(idObj);
        for (int i = 0; i < tmp.length; i++) {
            bytes[i + 4] = tmp[tmp.length - 1 - i];
        }
        tmp = getBytesFromInt(idDc);
        for (int i = 0; i < tmp.length; i++) {
            bytes[i + 8] = tmp[tmp.length - 1 - i];
        }
        bytes[12] = 0x09;
        bytes[13] = 0x00;
        bytes[16] = 0x2a;
        bytes[17] = 0x3c;
        bytes[18] = 0x46;
        bytes[19] = 0x4c;
        bytes[20] = 0x45;
        bytes[21] = 0x58;
        bytes[22] = Byte.parseByte(Integer.toString(protocol));
        bytes[23] = Byte.parseByte(Integer.toString(protocol_version));
        bytes[24] = Byte.parseByte(Integer.toString(struct_version));
        bytes[14] = (byte)getCRC(Arrays.copyOfRange(bytes, 16, 19));
        bytes[15] = (byte)getCRC(Arrays.copyOfRange(bytes, 0, 15));
        return bytes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message{preambula=");
        sb.append(preambula);
        sb.append(", idObj=");
        sb.append(idObj);
        sb.append(", idDc=");
        sb.append(idDc);
        sb.append(", size=");
        sb.append(size);
        sb.append(", csd=");
        sb.append(csd);
        sb.append(", csp=");
        sb.append(csp);
        sb.append(", imei=");
        sb.append(imei);
        sb.append(", protocol=");
        sb.append(protocol);
        sb.append(", protocol version=");
        sb.append(protocol_version);
        sb.append(", structure version=");
        sb.append(struct_version);
        sb.append(", data size=");
        sb.append(data_size);
        sb.append(", bit field=");
        sb.append(bitField);
        sb.append("}");
        return sb.toString();
    }

//    public static void main(String[] args) {
//        MessageToSetSetting msg = new MessageToSetSetting();
//        byte[] bytes = new byte[35];
//        bytes[0] = 0x40;
//        bytes[1] = 0x4e;
//        bytes[2] = 0x54;
//        bytes[3] = 0x43;
//        bytes[4] = 0x01;
//        bytes[5] = 0x00;
//        bytes[6] = 0x00;
//        bytes[7] = 0x00;
//        bytes[8] = 0x00;
//        bytes[9] = 0x00;
//        bytes[10] = 0x00;
//        bytes[11] = 0x00;
//        bytes[12] = 0x13;
//        bytes[13] = 0x00;
//        bytes[14] = (byte)0xfb;
//        bytes[15] = (byte)0xf0;
//        bytes[16] = 0x2a;
//        bytes[17] = 0x3e;
//        bytes[18] = 0x46;
//        bytes[19] = 0x4c;
//        bytes[20] = 0x45;
//        bytes[21] = 0x58;
//        bytes[22] = (byte)0xb0;
//        bytes[23] = 0x0a;
//        bytes[24] = 0x0a;
//        bytes[25] = 0x45;
//        bytes[26] = (byte)0xe1;
//        bytes[27] = (byte)0xec;
//        bytes[28] = 0x00;
//        bytes[29] = 0x00;
//        bytes[30] = 0x00;
//        bytes[31] = 0x00;
//        bytes[32] = 0x00;
//        bytes[33] = 0x00;
//        bytes[34] = 0x00;
//        System.out.println(Arrays.toString(bytes));
//        msg.fromByteArray(bytes);
//    }
}
