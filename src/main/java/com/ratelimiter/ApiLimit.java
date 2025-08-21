package com.ratelimiter;

public class ApiLimit {
    private int capacity;
    private double refillTokensPerSecond;


    public ApiLimit() {}


    public ApiLimit(int capacity, double refillTokensPerSecond) {
        if (capacity <= 0 || refillTokensPerSecond <= 0) {
            throw new IllegalArgumentException("Invalid limit values");
        }
        this.capacity = capacity;
        this.refillTokensPerSecond = refillTokensPerSecond;
    }


    public int getCapacity() { return capacity; }
    public double getRefillTokensPerSecond() { return refillTokensPerSecond; }


    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setRefillTokensPerSecond(double refillTokensPerSecond) { this.refillTokensPerSecond = refillTokensPerSecond; }

    @Override
    public String toString() {
        return "ApiLimit{" +
                "capacity=" + capacity +
                ", refillTokensPerSecond=" + refillTokensPerSecond +
                '}';
    }
}
