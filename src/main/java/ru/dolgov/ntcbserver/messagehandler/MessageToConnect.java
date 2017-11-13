package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

public class MessageToConnect extends Message {

    public MessageToConnect() {
    }

    @Override
    public void fromByteArray(byte[] bytes)     {
        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 4));
        idDc = getId(Arrays.copyOfRange(bytes, 4, 8));
        idObj = getId(Arrays.copyOfRange(bytes, 8, 12));
        size = getId(Arrays.copyOfRange(bytes, 12, 14));
        csd = getCRC(Arrays.copyOfRange(bytes, 16, 35));
        csp = getCRC(Arrays.copyOfRange(bytes, 0, 15));
        imei = getImei(Arrays.copyOfRange(bytes, 20, 35));
        System.out.println(toString());
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[19];
        bytes[0] = 0x40;
        bytes[1] = 0x4e;
        bytes[2] = 0x54;
        bytes[3] = 0x43;
        byte[] tmp = setID(idObj);
        for (int i = 0; i < tmp.length; i++) {
            bytes[i + 4] = tmp[tmp.length - 1 - i];
        }
        tmp = setID(idDc);
        for (int i = 0; i < tmp.length; i++) {
            bytes[i + 8] = tmp[tmp.length - 1 - i];
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
        sb.append("}");
        return sb.toString();
    }
}
