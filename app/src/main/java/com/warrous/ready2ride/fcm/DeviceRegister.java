package com.warrous.ready2ride.fcm;

/**
 * Created by chaithanyav on 10/25/2017.
 */

public class DeviceRegister {

    private String deviceName;
    private String deviceId;
    private String registrationId;
    private String deviceOs;



    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    @Override
    public String toString() {
        return "DeviceRegister{" +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", deviceOs='" + deviceOs + '\'' +
                '}';
    }
}
