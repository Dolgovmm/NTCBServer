package ru.dolgov.ntcbserver.Entity;

public class TelemetricFrame {
    private long imei;
    private double lalitude;
    private double longitude;
    private double speed;
    private long course;

    public TelemetricFrame(){
        this.imei = -1;
        this.lalitude = -1;
        this.longitude = -1;
        this.speed = -1;
        this.course = -1;
    }

    public TelemetricFrame(long imei, double lalitude, double longitude, double speed, long course) {
        this.imei = imei;
        this.lalitude = lalitude;
        this.longitude = longitude;
        this.speed = speed;
        this.course = course;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public double getLalitude() {
        return lalitude;
    }

    public void setLalitude(double lalitude) {
        this.lalitude = lalitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getCourse() {
        return course;
    }

    public void setCourse(long course) {
        this.course = course;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TelemetricFrame{imei=");
        sb.append(imei);
        sb.append(", lalitude=");
        sb.append(lalitude);
        sb.append(", longitude=");
        sb.append(longitude);
        sb.append(", speed=");
        sb.append(speed);
        sb.append(", course=");
        sb.append(course);
        sb.append("}");
        return sb.toString();
    }
}
