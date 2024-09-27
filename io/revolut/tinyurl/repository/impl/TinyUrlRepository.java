package io.revolut.tinyurl.repository.impl;

import io.revolut.tinyurl.repository.IRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TinyUrlRepository implements IRepository {
    private final AtomicInteger index;
    private final Map<String, String> urlToTinyUrl;
    private final Map<String, String> tinyUrlToUrl;

    public TinyUrlRepository() {
        this.index = new AtomicInteger(0);
        this.urlToTinyUrl = new ConcurrentHashMap<>();
        this.tinyUrlToUrl = new ConcurrentHashMap<>();
    }

    @Override
    public void store(String url, String tinyUrl) {
        urlToTinyUrl.putIfAbsent(url, tinyUrl);
        tinyUrlToUrl.putIfAbsent(tinyUrl, url);
        this.index.incrementAndGet();
    }

    @Override
    public String getTinyUrl(String url) {
        return urlToTinyUrl.get(url);
    }

    @Override
    public String getUrl(String tinyUrl) {
        return tinyUrlToUrl.get(tinyUrl);
    }

    @Override
    public boolean containsUrl(String url) {
        return urlToTinyUrl.containsKey(url);
    }

    @Override
    public boolean containsTinyUrl(String tinyUrl) {
        return tinyUrlToUrl.containsKey(tinyUrl);
    }

    @Override
    public int size() {
        return tinyUrlToUrl.size();
    }

    @Override
    public int getIndex() {
        return this.index.get();
    }
}
