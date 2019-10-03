package com.airgraft.services.apiaccess.services;

import net.iakovlev.timeshape.TimeZoneEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Optional;

/**
 * In-Memory Timezone Service which delegate the work to timeshape.
 */
@Service
public class TimezoneServiceImpl implements TimezoneService {
    static Logger LOG = LoggerFactory.getLogger(TimezoneService.class);

    TimeZoneEngine engine;

    @PostConstruct
    private void init() {
        LOG.info("Initializing Service >>> ");

        LOG.info(" ... Loading Timezone Database ");
        engine = TimeZoneEngine.initialize();

        LOG.info("<<< Initializing Service ");

    }

    @Override
    public String getTimezone(Double latitude, Double longitude) {
        Optional<ZoneId> maybeZoneId = engine.query(latitude, longitude);
        if (!maybeZoneId.isPresent()) {
            return null;
        }

        return maybeZoneId.get().toString();
    }
}
