package com.ratelimiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimiterConfigLoaderTest {

    @Test
    void loadFromPropertiesFile() {
        // Load the test properties file from resources
        RateLimiterConfig cfg = RateLimiterConfigLoader.loadFromProperties("application.properties");

        assertNotNull(cfg, "Config should not be null");

        // Default values
        ApiLimit def = cfg.getDefault();
        assertNotNull(def, "Default limit must be present");
        assertEquals(10, def.getCapacity());
        assertEquals(5.0, def.getRefillTokensPerSecond());

        // API-specific override: GET:/orders
        ApiLimit getOrders = cfg.getApis().get("GET:/orders");
        assertNotNull(getOrders, "GET:/orders limit must be loaded");
        assertEquals(20, getOrders.getCapacity());
        assertEquals(10.0, getOrders.getRefillTokensPerSecond());

        // API-specific override: POST:/orders
        ApiLimit postOrders = cfg.getApis().get("POST:/orders");
        assertNotNull(postOrders, "POST:/orders limit must be loaded");
        assertEquals(5, postOrders.getCapacity());
        assertEquals(2.0, postOrders.getRefillTokensPerSecond());
    }
}