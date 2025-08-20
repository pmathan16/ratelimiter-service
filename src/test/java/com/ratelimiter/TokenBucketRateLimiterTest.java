package com.ratelimiter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TokenBucketRateLimiterTest {


    @Test
    void defaultLimitApplied() {
        RateLimiterConfig cfg = new RateLimiterConfig(new ApiLimit(3, 1), java.util.Map.of());
        RateLimiter limiter = new TokenBucketRateLimiter(cfg);
        int allowed = 0;
        for (int i = 0; i < 5; i++) if (limiter.allow("u1", "X")) allowed++;
        assertTrue(allowed <= 3);
    }


    @Test
    void apiSpecificOverride() {
        RateLimiterConfig cfg = new RateLimiterConfig(new ApiLimit(2, 1), java.util.Map.of("GET:/orders", new ApiLimit(5, 5)));
        RateLimiter limiter = new TokenBucketRateLimiter(cfg);
        int allowed = 0;
        for (int i = 0; i < 6; i++) if (limiter.allow("u1", "GET:/orders")) allowed++;
        assertTrue(allowed >= 5);
    }
}
