package ru.dolgov.ntcbserver.messagehandler;

public abstract class Message {
    protected String preambula;
    protected int idObj;
    protected int idDc;
    protected int size;
    protected int csd;
    protected int csp;
    protected long imei;
//    private int protocol;
//    private int protocolVersion;
//    private int structureVersion;
//    private int dataSize;
//    private int bitField;
//    private TelemetricFrame frame;

//    public String getPreambula() {
//        return preambula;
//    }
//
//    public void setPreambula(String preambula) {
//        this.preambula = preambula;
//    }
//
//    public int getIdObj() {
//        return idObj;
//    }
//
//    public void setIdObj(int idObj) {
//        this.idObj = idObj;
//    }
//
//    public int getIdDc() {
//        return idDc;
//    }
//
//    public void setIdDc(int idDc) {
//        this.idDc = idDc;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }
//
//    public int getCsd() {
//        return csd;
//    }
//
//    public void setCsd(int csd) {
//        this.csd = csd;
//    }
//
//    public int getCsp() {
//        return csp;
//    }
//
//    public void setCsp(int csp) {
//        this.csp = csp;
//    }
//
//    public long getImei() {
//        return imei;
//    }
//
//    public void setImei(long imei) {
//        this.imei = imei;
//    }

//    public int getProtocol() {
//        return protocol;
//    }
//
//    public void setProtocol(int protocol) {
//        this.protocol = protocol;
//    }
//
//    public int getProtocolVersion() {
//        return protocolVersion;
//    }
//
//    public void setProtocolVersion(int protocolVersion) {
//        this.protocolVersion = protocolVersion;
//    }
//
//    public int getStructureVersion() {
//        return structureVersion;
//    }
//
//    public void setStructureVersion(int structureVersion) {
//        this.structureVersion = structureVersion;
//    }
//
//    public int getDataSize() {
//        return dataSize;
//    }
//
//    public void setDataSize(int dataSize) {
//        this.dataSize = dataSize;
//    }
//
//    public int getBitField() {
//        return bitField;
//    }
//
//    public void setBitField(int bitField) {
//        this.bitField = bitField;
//    }
//
//    public TelemetricFrame getFrame() {
//        return frame;
//    }
//
//    public void setFrame(TelemetricFrame frame) {
//        this.frame = frame;
//    }

    public abstract void fromByteArray(byte[] bytes);
//    {
//        preambula = getPreambula(Arrays.copyOfRange(bytes, 0, 4));
//        setIdDc(getId(Arrays.copyOfRange(bytes, 4, 8)));
//        setIdObj(getId(Arrays.copyOfRange(bytes, 8, 12)));
//        setSize(getId(Arrays.copyOfRange(bytes, 12, 14)));
//        setCsd(getCRC(Arrays.copyOfRange(bytes, 16, 35)));
//        setCsp(getCRC(Arrays.copyOfRange(bytes, 0, 15)));
//        setImei(getImei(Arrays.copyOfRange(bytes, 20, 35)));
//        System.out.println(toString());
//    }

    public abstract byte[] toByteArray();
//    {
//        byte[] bytes = new byte[19];
//        bytes[0] = 0x40;
//        bytes[1] = 0x4e;
//        bytes[2] = 0x54;
//        bytes[3] = 0x43;
//        byte[] idObj = setID(getIdObj());
//        for (int i = 0; i < idObj.length; i++) {
//            bytes[i + 4] = idObj[idObj.length - 1 - i];
//        }
//        byte[] iddc = setID(getIdDc());
//        for (int i = 0; i < iddc.length; i++) {
//            bytes[i + 8] = iddc[idObj.length - 1 - i];
//        }
//        bytes[12] = 0x03;
//        bytes[13] = 0x00;
//        bytes[16] = 0x2a;
//        bytes[17] = 0x3c;
//        bytes[18] = 0x53;
//        bytes[14] = (byte)getCRC(Arrays.copyOfRange(bytes, 16, 19));
//        bytes[15] = (byte)getCRC(Arrays.copyOfRange(bytes, 0, 15));
//        return bytes;
//    }

//    private byte[] checkFrame(byte[] bytes) {
//        byte[] response = new byte[1];
//        return response;
//    }

    protected String getPreambula(byte[] bytes) {
        return new String(getCharArray(bytes));
    }

    protected long getImei(byte[] bytes) {
        return Long.parseLong(new String(getCharArray(bytes)));
    }

    protected int getId(byte[] bytes) {
        int id = bytes[0] & 0xFF;
        for (int i = bytes.length - 1; i > 0; i--) {
            id = id | ((bytes[i] & 0xFF) << (i * 8));
        }
        return id;
    }

    protected byte[] setID(int value) {
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

    protected char getCRC(byte[] bytes) {
        int summ = 0;
        for (int i = 0; i < bytes.length; i++) {
            summ ^= bytes[i];
        }
        return (char)(summ & 0xFF);
    }

    public static char[] getCharArray(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char)bytes[i];
        }
        return chars;
    }

    protected byte crc8 (byte[] buffer)
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

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Message{preambula=");
//        sb.append(preambula);
//        sb.append(", idObj=");
//        sb.append(idObj);
//        sb.append(", idDc=");
//        sb.append(idDc);
//        sb.append(", size=");
//        sb.append(size);
//        sb.append(", csd=");
//        sb.append(csd);
//        sb.append(", csp=");
//        sb.append(csp);
//        sb.append(", imei=");
//        sb.append(imei);
//        sb.append(", protocol=");
//        sb.append(protocol);
//        sb.append(", protocolVersion=");
//        sb.append(protocolVersion);
//        sb.append(", structureVersion=");
//        sb.append(structureVersion);
//        sb.append(", dataSize=");
//        sb.append(dataSize);
//        sb.append("}");
//        return sb.toString();
//    }
}
