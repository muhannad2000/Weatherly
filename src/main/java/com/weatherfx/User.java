package com.weatherfx;

public class User {

    private String username;
    private String password;
    private String ipAddress;
    private String cityLocation;

    public User(String username, String password, String ipAddress, String cityLocation) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
        this.cityLocation = cityLocation;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getCityLocation() {
        return cityLocation;
    }
}