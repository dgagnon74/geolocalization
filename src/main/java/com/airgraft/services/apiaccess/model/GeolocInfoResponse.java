package com.airgraft.services.apiaccess.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Geolocalization information related to a given longitude & latitude ")
public class GeolocInfoResponse {

    @ApiModelProperty(notes = "The longitude has a double value")
    private Double longitude;
    @ApiModelProperty(notes = "The latitude has a double value")
    private Double latitude;

    @ApiModelProperty(notes = "The corresponding timezone in the IANA format i.e. 'AAAA/BBBB', e.g. 'America/Toronto', 'Europe/Berlin'")
    private String timezoneId;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }
}
