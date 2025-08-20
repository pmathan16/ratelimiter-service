package com.ratelimiter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RateLimiterConfigLoader {


    public static RateLimiterConfig loadFromProperties(String resource) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (is == null) throw new IllegalArgumentException("Resource not found: " + resource);
            Properties props = new Properties();
            props.load(is);


            int defCap = Integer.parseInt(props.getProperty("default.capacity", "10"));
            double defRate = Double.parseDouble(props.getProperty("default.refillTokensPerSecond", "5"));
            ApiLimit defaultLimit = new ApiLimit(defCap, defRate);


            Map<String, ApiLimit> apis = new HashMap<>();
            for (String key : props.stringPropertyNames()) {
                if (key.startsWith("api.")) {
                    String[] parts = key.split("\\.");
                    if (parts.length == 3) {
                        String apiId = parts[1];
                        ApiLimit existing = apis.getOrDefault(apiId, new ApiLimit(defCap, defRate));
                        if ("capacity".equals(parts[2])) {
                            existing.setCapacity(Integer.parseInt(props.getProperty(key)));
                        } else if ("refillTokensPerSecond".equals(parts[2])) {
                            existing.setRefillTokensPerSecond(Double.parseDouble(props.getProperty(key)));
                        }
                        apis.put(apiId, existing);
                    }
                }
            }


            return new RateLimiterConfig(defaultLimit, apis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config from " + resource, e);
        }
    }
}