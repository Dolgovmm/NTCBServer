package ru.dolgov.ntcbserver.messagehandler;

import ru.dolgov.ntcbserver.Entity.TelemetricFrame;

public class Message {
    private String preambula;
    private int idObj;
    private int idDc;
    private int size;
    private int csd;
    private int csp;
    private long imei;
    private int protocol;
    private int protocolVersion;
    private int structureVersion;
    private int dataSize;
    private int bitField;
    private TelemetricFrame frame;

    public Message() {
    }

    public Message(String preambula, int idObj, int idDc, int size, int csd, int csp, long imei, int protocol, int protocolVersion, int structureVersion, int dataSize, int bitField, TelemetricFrame frame) {
        this.preambula = preambula;
        this.idObj = idObj;
        this.idDc = idDc;
        this.size = size;
        this.csd = csd;
        this.csp = csp;
        this.imei = imei;
        this.protocol = protocol;
        this.protocolVersion = protocolVersion;
        this.structureVersion = structureVersion;
        this.dataSize = dataSize;
        this.bitField = bitField;
        this.frame = frame;
    }

    public String getPreambula() {
        return preambula;
    }

    public void setPreambula(String preambula) {
        this.preambula = preambula;
    }

    public int getIdObj() {
        return idObj;
    }

    public void setIdObj(int idObj) {
        this.idObj = idObj;
    }

    public int getIdDc() {
        return idDc;
    }

    public void setIdDc(int idDc) {
        this.idDc = idDc;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCsd() {
        return csd;
    }

    public void setCsd(int csd) {
        this.csd = csd;
    }

    public int getCsp() {
        return csp;
    }

    public void setCsp(int csp) {
        this.csp = csp;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getStructureVersion() {
        return structureVersion;
    }

    public void setStructureVersion(int structureVersion) {
        this.structureVersion = structureVersion;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getBitField() {
        return bitField;
    }

    public void setBitField(int bitField) {
        this.bitField = bitField;
    }

    public TelemetricFrame getFrame() {
        return frame;
    }

    public void setFrame(TelemetricFrame frame) {
        this.frame = frame;
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
        sb.append(", protocolVersion=");
        sb.append(protocolVersion);
        sb.append(", structureVersion=");
        sb.append(structureVersion);
        sb.append(", dataSize=");
        sb.append(dataSize);
        sb.append("}");

        return sb.toString();
    }
}
