package ru.dolgov.ntcbserver.Entity;

import java.util.List;

public class TelemetricStation {
    private int idObj;
    private int idDc;
    private int size;
    private int csd;
    private int csp;
    private int imei;
    private int protocol;
    private int protocolVersion;
    private int structureVersion;
    private int dataSize;
    private int bitField;
    private List<TelemetricFrame> frameList;
}
