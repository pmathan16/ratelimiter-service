package com.ratelimiter;

import java.util.concurrent.TimeUnit;


class TokenBucket {
    private final int capacity;
    private final double refillPerNanos;


    private double tokens;
    private long lastRefillNanos;


    TokenBucket(int capacity, double refillTokensPerSecond) {
        this.capacity = capacity;
        this.refillPerNanos = refillTokensPerSecond / TimeUnit.SECONDS.toNanos(1);
        this.tokens = capacity;
        this.lastRefillNanos = System.nanoTime();
    }


    synchronized boolean tryConsume() {
        refill();
        if (tokens >= 1.0d) {
            tokens -= 1.0d;
            return true;
        }
        return false;
    }


    private void refill() {
        long now = System.nanoTime();
        long delta = now - lastRefillNanos;
        if (delta > 0) {
            tokens = Math.min(capacity, tokens + delta * refillPerNanos);
            lastRefillNanos = now;
        }
    }
}
