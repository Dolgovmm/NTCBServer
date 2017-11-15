package ru.dolgov.ntcbserver.messagehandler;

import java.util.Arrays;

public class MessageWithFrame extends Message {
    private int numPage;
    private int code;
    private long time;
    private int stageNGaude;
    private long lastTime;
    private long latitude;
    private long longtitude;
    private int speed;
    private int course;
    private int crc;

    @Override
    public void fromByteArray(byte[] bytes) {
        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 2));
        numPage = getIntFromBytes(Arrays.copyOfRange(bytes, 2, 6));
        code = getIntFromBytes(Arrays.copyOfRange(bytes, 6, 8));
        time = getIntFromBytes(Arrays.copyOfRange(bytes, 8, 12));
        stageNGaude = bytes[13];
        lastTime = getIntFromBytes(Arrays.copyOfRange(bytes, 13, 17));
        latitude = getIntFromBytes(Arrays.copyOfRange(bytes, 17, 21));
        longtitude = getIntFromBytes(Arrays.copyOfRange(bytes, 21, 25));
        speed = getIntFromBytes(Arrays.copyOfRange(bytes, 25, 27));
        course = getIntFromBytes(Arrays.copyOfRange(bytes, 27, 29));
        crc = bytes[29];
        System.out.println(toString());
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[2];
        bytes[0] = 0x7e;
        bytes[1] = 0x43;
        bytes[2] = crc8(bytes);
        return bytes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message{preambula=");
        sb.append(preambula);
        sb.append(", num page=");
        sb.append(numPage);
        sb.append(", code=");
        sb.append(code);
        sb.append(", time");
        sb.append(time);
        sb.append(", stage N gaude=");
        sb.append(stageNGaude);
        sb.append(", last time=");
        sb.append(lastTime);
        sb.append(", latitude=");
        sb.append(latitude);
        sb.append(", longtitude=");
        sb.append(longtitude);
        sb.append(", speed=");
        sb.append(speed);
        sb.append(", course=");
        sb.append(course);
        sb.append(", crc=");
        sb.append(crc);
        sb.append("}");
        return sb.toString();
    }
}
