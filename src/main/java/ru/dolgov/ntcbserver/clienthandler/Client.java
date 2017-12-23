package ru.dolgov.ntcbserver.clienthandler;

import java.util.Arrays;

public class Client {
	
	private int idDc;
	private int idObj;
	private int size;
	private long imei;
	private int protocol;
	private int protocolVersion;
	private int structureVersion;
	private int dataSize;
	private boolean[] settings;
	private int numberOfPage;
	private int code;
	private int time;
	private int stage;
	private long lastTime;
	private int lantitude;
	private int lontitude;
	private double speed;
	private int course;
	
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getDataSize() {
		return dataSize;
	}
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
	public int getStructureVersion() {
		return structureVersion;
	}
	public void setStructureVersion(int structureVersion) {
		this.structureVersion = structureVersion;
	}
	public int getProtocolVersion() {
		return protocolVersion;
	}
	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public int getIdDc() {
		return idDc;
	}
	public void setIdDc(int idDc) {
		this.idDc = idDc;
	}
	public int getIdObj() {
		return idObj;
	}
	public void setIdObj(int idObj) {
		this.idObj = idObj;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getImei() {
		return imei;
	}
	public void setImei(long imei) {
		this.imei = imei;
	}
	public boolean[] getSettings() {
		return settings;
	}
	public void setSettings(boolean[] settings) {
		this.settings = settings;
	}
	public int getNumberOfPage() {
		return numberOfPage;
	}
	public void setNumberOfPage(int numberOfPage) {
		this.numberOfPage = numberOfPage;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public int getLantitude() {
		return lantitude;
	}
	public void setLantitude(int lantitude) {
		this.lantitude = lantitude;
	}
	public int getLontitude() {
		return lontitude;
	}
	public void setLontitude(int lontitude) {
		this.lontitude = lontitude;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getCourse() {
		return course;
	}
	public void setCourse(int course) {
		this.course = course;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{idDc=");
		sb.append(idDc);
		sb.append(", idObj=");
		sb.append(idObj);
		sb.append(", size=");
		sb.append(size);
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
		sb.append(", settings=");
		sb.append(Arrays.toString(settings));
		sb.append(", numberOfPage=");
		sb.append(numberOfPage);
		sb.append(", code=");
		sb.append(code);
		sb.append(", time=");
		sb.append(time);
		sb.append(", stage=");
		sb.append(stage);
		sb.append(", lastTime=");
		sb.append(lastTime);
		sb.append(", lantitude=");
		sb.append(lantitude);
		sb.append(", lontitude=");
		sb.append(lontitude);
		sb.append(", speed=");
		sb.append(speed);
		sb.append(", course=");
		sb.append(course);
		return sb.toString();
	}
}
