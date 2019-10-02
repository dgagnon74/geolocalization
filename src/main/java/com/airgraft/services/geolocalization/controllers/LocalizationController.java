package com.airgraft.services.geolocalization.controllers;

import com.airgraft.services.geolocalization.model.GeolocInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "v1/localization")
public class LocalizationController {

    static Logger LOG = LoggerFactory.getLogger(LocalizationController.class);

    @RequestMapping(value = "/timezones", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public static ResponseEntity<GeolocInfo> getLocation(@RequestParam(name = "longitude") Double longitude, @RequestParam(name = "latitude") Double latitude) {
        LOG.info("REST request health check");
        GeolocInfo geolocInfo = new GeolocInfo();
        geolocInfo.setLongitude( longitude);
        geolocInfo.setLatitude(latitude);
        geolocInfo.setTimezoneId("abc");
        return new ResponseEntity<>(geolocInfo, HttpStatus.OK);
    }


}
