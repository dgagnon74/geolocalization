package com.airgraft.services.geolocalization.controllers;

import com.airgraft.services.geolocalization.model.GeolocInfo;
import com.airgraft.services.geolocalization.services.TimezoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.iakovlev.timeshape.TimeZoneEngine;

import java.util.Optional;
import java.time.ZoneId;


import javax.annotation.PostConstruct;


@RestController
@RequestMapping(value = "api/localization/v1")
public class LocalizationController {

    static Logger LOG = LoggerFactory.getLogger(LocalizationController.class);

    @Autowired
    TimezoneService timezoneService;

    @GetMapping(value = "/timezones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeolocInfo> getLocation(
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "latitude") Double latitude
    ) {
        LOG.info("Get timezone from position called longitude = " + longitude + " latitude = " + latitude);
        GeolocInfo geolocInfo = new GeolocInfo();
        geolocInfo.setLongitude(longitude);
        geolocInfo.setLatitude(latitude);

        String timezoneId = timezoneService.getTimezone(latitude, longitude);

        if (timezoneId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        geolocInfo.setTimezoneId(timezoneId);
        return new ResponseEntity<>(geolocInfo, HttpStatus.OK);
    }


}
