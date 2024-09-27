package io.revolut.tinyurl.domain.encoder.impl;

import io.revolut.tinyurl.domain.encoder.IEncoder;

import java.util.concurrent.locks.StampedLock;

public class Base64UrlEncoder implements IEncoder {
    private static final String BASE_64_KEY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final StampedLock lock;

    public Base64UrlEncoder() {
        this.lock = new StampedLock();
    }

    @Override
    public String encode(int index) {
        var writeLock = lock.writeLock();
        try {
            char []keys = BASE_64_KEY.toCharArray();
            int limit = keys.length;
            var builder = new StringBuilder();

            while(index > 0) {
                builder.append(keys[index % limit]);
                index /= limit;
            }
            return builder.toString();
        } finally {
            lock.unlockWrite(writeLock);
        }
    }
}
