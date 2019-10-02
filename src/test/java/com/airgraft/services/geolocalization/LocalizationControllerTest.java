package com.airgraft.services.geolocalization;

import com.airgraft.services.geolocalization.controllers.LocalizationController;
import com.airgraft.services.geolocalization.services.TimezoneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocalizationController.class)
public class LocalizationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TimezoneService service;

    @Test
    public void test200() throws Exception {
        mvc.perform(get("/api/localization/v1/timezones?latitude=45.508888&longitude=-73.561668")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("timezoneId", is("America/Toronto")));
    }

    @Test
    public void testBadRequest() throws Exception {
        mvc.perform(get("/api/localization/v1/timezones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNotFoundRequest() throws Exception {
        mvc.perform(get("/api/localization/v1/timezones?latitude=11245&longitude=-2000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}