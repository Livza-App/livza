package com.example.livza.FireClasses;

public class Adresse {
    private Double latitude, longitude;
    private String name;

    public Adresse(Double latitude, Double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public Adresse() {
    }
    //for test
    public Adresse(String name) {
        latitude=0.0;
        longitude=0.0;
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
