package ru.dolgov.ntcbserver.messagehandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MessageWithFrame extends Message {
    static final Logger logger = LoggerFactory.getLogger(MessageWithFrame.class);

    private int numPage;
    private int code;
    private long time;
    private int stageGaude;
    private long lastTime;
    private long latitude;
    private long longitude;
    private int speed;
    private int course;
    private int crc;

    @Override
    public void fromByteArray(byte[] bytes) {
        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 2));
        numPage = getIntFromBytes(Arrays.copyOfRange(bytes, 2, 6));
        code = getIntFromBytes(Arrays.copyOfRange(bytes, 6, 8));
        time = getIntFromBytes(Arrays.copyOfRange(bytes, 8, 12));
        stageGaude = bytes[12] & 0xFF;
        lastTime = getIntFromBytes(Arrays.copyOfRange(bytes, 13, 17));
        latitude = getIntFromBytes(Arrays.copyOfRange(bytes, 17, 21));
        longitude = getIntFromBytes(Arrays.copyOfRange(bytes, 21, 25));
        speed = getIntFromBytes(Arrays.copyOfRange(bytes, 25, 29));
        course = getIntFromBytes(Arrays.copyOfRange(bytes, 29, 31));
        crc = crc8(Arrays.copyOfRange(bytes, 2, 30)) & 0xFF;
        logger.info(toLog(bytes));
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[3];
        bytes[0] = 0x7e;
        bytes[1] = 0x43;
        bytes[2] = crc8(Arrays.copyOfRange(bytes, 0, 2));

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
        sb.append(", stage gaude=");
        sb.append(stageGaude);
        sb.append(", last time=");
        sb.append(lastTime);
        sb.append(", latitude=");
        sb.append(latitude);
        sb.append(", longtitude=");
        sb.append(longitude);
        sb.append(", speed=");
        sb.append(speed);
        sb.append(", course=");
        sb.append(course);
        sb.append(", crc=");
        sb.append(crc);
        sb.append("}");
        return sb.toString();
    }

    public String toLog(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        sb.append(" принято ");
        sb.append(bytes.length);
        sb.append(" байт: ");
        for (int i = 0; i < bytes.length; i++) {
            sb.append("0x");
            sb.append(Integer.toHexString(bytes[i]));
            sb.append(", ");
        }
        sb.append("\n {preambula=");
        sb.append(preambula);
        sb.append(", num page=");
        sb.append(numPage);
        sb.append(", code=");
        sb.append(code);
        sb.append(", time");
        sb.append(time);
        sb.append(", stage gaude=");
        sb.append(stageGaude);
        sb.append(", last time=");
        sb.append(lastTime);
        sb.append(", latitude=");
        sb.append(latitude);
        sb.append(", longtitude=");
        sb.append(longitude);
        sb.append(", speed=");
        sb.append(speed);
        sb.append(", course=");
        sb.append(course);
        sb.append(", crc=");
        sb.append(crc);
        sb.append("} \n");
        return sb.toString();
    }
}
