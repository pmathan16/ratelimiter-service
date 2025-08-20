package com.ratelimiter;

import com.ratelimiter.*;


public class Main {
    public static void main(String[] args) throws Exception {
        RateLimiterConfig config = RateLimiterConfigLoader.loadFromProperties("application.properties");
        RateLimiter limiter = RateLimiterFactory.tokenBucket(config);


        String user = "user123";
        String api = "GET:/orders";


        int allowed = 0, blocked = 0;
        for (int i = 0; i < 30; i++) {
            if (limiter.allow(user, api)) allowed++; else blocked++;
            Thread.sleep(50);
        }
        System.out.printf("Allowed=%d, Blocked=%d%n", allowed, blocked);
    }
}