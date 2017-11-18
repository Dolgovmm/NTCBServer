package ru.dolgov.ntcbserver.messagehandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MessageToConnect extends Message {

    static final Logger logger = LoggerFactory.getLogger(MessageToConnect.class);

    @Override
    public void fromByteArray(byte[] bytes)     {
        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 4));
        idDc = getIntFromBytes(Arrays.copyOfRange(bytes, 4, 8));
        idObj = getIntFromBytes(Arrays.copyOfRange(bytes, 8, 12));
        size = getIntFromBytes(Arrays.copyOfRange(bytes, 12, 14));
        csd = getCRC(Arrays.copyOfRange(bytes, 16, 35));
        csp = getCRC(Arrays.copyOfRange(bytes, 0, 15));
        imei = getImei(Arrays.copyOfRange(bytes, 20, 35));
        logger.info(toLog(bytes));
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[19];
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
        bytes[12] = 0x03;
        bytes[13] = 0x00;
        bytes[16] = 0x2a;
        bytes[17] = 0x3c;
        bytes[18] = 0x53;
        bytes[14] = getCRC(Arrays.copyOfRange(bytes, 16, 19));
        bytes[15] = getCRC(Arrays.copyOfRange(bytes, 0, 15));
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

    public String toLog(byte[] bytes) {
        String digits = "0123456789abcdef";
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        sb.append(" принято ");
        sb.append(bytes.length);
        sb.append(" байт: ");
        for (int i = 0; i < bytes.length; i++) {
            sb.append("0x");
            int v = bytes[i] & 0xff;
            sb.append(digits.charAt(v >> 4));
            sb.append(digits.charAt(v & 0xf));
            //sb.append(Integer.toHexString(bytes[i]));
            sb.append(", ");
        }
        sb.append("\n {preambula=");
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
        sb.append("} \n");
        return sb.toString();
    }
}
