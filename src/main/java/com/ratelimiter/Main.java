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

            try {
                if (limiter.allow(user, api)) allowed++;
                else {
                    blocked++;
                    throw new RateLimitExceededException("Too many requests");
                }
            } catch (RateLimitExceededException rl) {
                System.out.println("RateLimiter " + rl.getMessage());
            } catch (Exception e) {
                System.out.println("Exception Occured " + e.getMessage());
            }
        }
        System.out.printf("Allowed=%d, Blocked=%d%n", allowed, blocked);

    }
}