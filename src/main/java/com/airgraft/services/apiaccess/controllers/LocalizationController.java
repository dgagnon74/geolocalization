package com.airgraft.services.apiaccess.controllers;

import com.airgraft.services.apiaccess.model.GeolocInfoResponse;
import com.airgraft.services.apiaccess.services.TimezoneService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/localization/v1")
public class LocalizationController {

    static Logger LOG = LoggerFactory.getLogger(LocalizationController.class);

    @Autowired
    TimezoneService timezoneService;

    @ApiOperation(value = "Obtain the timezone in the IANA format (i.e. 'AAAA/BBBB', e.g. 'America/Toronto', 'Europe/Berlin') for a longitude & latitude",
            response = GeolocInfoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the timezone"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource (Wrong/Missing apikey)"),
            @ApiResponse(code = 400, message = "Your request is not well formed"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/timezones", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeolocInfoResponse> getLocation(
            @RequestParam(name = "longitude")
            @ApiParam(value = "The longitude, ex: -73.561668", required = true) Double longitude,
            @RequestParam(name = "latitude")
            @ApiParam(value = "The latitude, ex: 45.508888", required = true) Double latitude) {
        LOG.info("Get timezone from position (longitude = " + longitude + " latitude = " + latitude + ")");

        GeolocInfoResponse geolocInfoResponse = new GeolocInfoResponse();
        geolocInfoResponse.setLongitude(longitude); // To ease message tracing
        geolocInfoResponse.setLatitude(latitude);

        String timezoneId = timezoneService.getTimezone(latitude, longitude);
        if (timezoneId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        geolocInfoResponse.setTimezoneId(timezoneId);
        return new ResponseEntity<>(geolocInfoResponse, HttpStatus.OK);
    }


}
