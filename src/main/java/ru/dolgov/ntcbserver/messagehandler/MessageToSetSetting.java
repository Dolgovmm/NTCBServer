package ru.dolgov.ntcbserver.messagehandler;

public class MessageToSetSetting extends Message {
    private int protocol;
    private int protocol_version;
    private int struct_version;
    private int data_size;
    private String bitField;

    @Override
    public void fromByteArray(byte[] bytes) {

    }

    @Override
    public byte[] toByteArray() {
        
        return new byte[0];
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
