package com.sahilkamat.services;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RateLimiter {

    private final int maxRequests;
    private final long timePeriodInMillis;
    private final Semaphore semaphore;
    private long lastRequestTime;

    public RateLimiter(int maxRequests, long timePeriodInMillis) {
        this.maxRequests = maxRequests;
        this.timePeriodInMillis = timePeriodInMillis;
        this.semaphore = new Semaphore(maxRequests);
        this.lastRequestTime = System.currentTimeMillis();
    }

    // Acquire method to check if the request can be made under the rate limit
    public synchronized boolean acquire() throws InterruptedException {
        long currentTime = System.currentTimeMillis();

        // Reset semaphore if the time period has passed
        if (currentTime - lastRequestTime > timePeriodInMillis) {
            semaphore.release(semaphore.availablePermits()); // Release all available permits
            lastRequestTime = currentTime; // Reset last request time
        }

        // Try acquiring a permit, if successful, continue
        return semaphore.tryAcquire(1, TimeUnit.MILLISECONDS);
    }
}