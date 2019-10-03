package com.airgraft.services.apiaccess.services;

public interface TimezoneService {
    /**
     * Get the timezone for the position in longitude/latitude
     *
     * @param latitude
     * @param longitude
     * @return the corresponding timezone in IANA format, null if nothing is found
     */
    String getTimezone(Double latitude, Double longitude);
}
