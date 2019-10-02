package com.airgraft.services.apiaccess.filters;

import com.fasterxml.jackson.databind.util.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class ApiKeyCheckFilter implements Filter {
    static Logger LOG = LoggerFactory.getLogger(ApiKeyCheckFilter.class);


    /**
     * Simple cache that shall be reset when receiving a async message that
     * API keys has changed from the pub/sub. Otherwise key can be cached
     */
    public LRUMap<String, ApiKeyInfo> apiKeyCache = new LRUMap(10, 100);

    /**
     * Default Key Validity Period
     */
    @Value("${apiKey.validityDelayMinute}")
    private Integer validityDelayMinute = 5;

    @Value("${apiKey.service.url}")
    private String serviceUrl;

    @Value("${apiKey.service.enabled}")
    private Boolean enabled = Boolean.TRUE;

    public ApiKeyCheckFilter() {
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!enabled || !req.getRequestURI().startsWith("/api/")) {
            chain.doFilter(request, response);
            return;
        }

        String apiKey = req.getParameter("api_key");
        if (apiKey == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "API Key not found in the request");
            return;
        }

        // Check if we have tha
        ApiKeyInfo cachedKeyInfo = apiKeyCache.get(apiKey);
        long validityLimitMilli = System.currentTimeMillis() + validityDelayMinute * 60 * 1000;
        if ((cachedKeyInfo == null) || (cachedKeyInfo.timestamp < validityLimitMilli)) {
            // Get ApiKey Info again

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ApiKeyInfo> apiKeyResponse = null;
            try {
                apiKeyResponse = restTemplate.getForEntity(serviceUrl + "/" + apiKey, ApiKeyInfo.class);
            } catch (ResourceAccessException e) {
                LOG.warn("Unable to access the server at " + serviceUrl + "/" + apiKey);
                res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Unable to access the server");
                return;
            }

            if (!apiKeyResponse.getStatusCode().equals(HttpStatus.OK)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Api Key not found");
                return;
            }

            cachedKeyInfo = apiKeyResponse.getBody();
            if (!cachedKeyInfo.active) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Api Key is not active");
                return;
            }

            apiKeyCache.put(apiKey, cachedKeyInfo);
        }

        chain.doFilter(request, response);
    }


    public static final class ApiKeyInfo {
        private String keyId;
        private Boolean active;
        private Long timestamp = System.currentTimeMillis();

        public String getKeyId() {
            return keyId;
        }

        public void setKeyId(String keyId) {
            this.keyId = keyId;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}