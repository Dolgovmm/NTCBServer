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
        protocol = bytes[22];
        protocol_version = bytes[23];
        struct_version = bytes[24];
        data_size = bytes[25];
        long l = getLongFromBytes(Arrays.copyOfRange(bytes, 26, 35));
        bitField = Long.toBinaryString(l);
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
        bytes[16] = 0x2a;// *
        bytes[17] = 0x3c;// >
        bytes[18] = 0x46;// F
        bytes[19] = 0x4c;// L
        bytes[20] = 0x45;// E
        bytes[21] = 0x58;// X
        bytes[22] = Byte.parseByte(Integer.toString(protocol));
        bytes[23] = Byte.parseByte(Integer.toString(protocol_version));
        bytes[24] = Byte.parseByte(Integer.toString(struct_version));
        bytes[14] = (byte)getCRC(Arrays.copyOfRange(bytes, 16, 25));
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
}
