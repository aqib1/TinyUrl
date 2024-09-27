package io.revolut.tinyurl.repository;

public interface IRepository {
    void store(String url, String tinyUrl);
    String getTinyUrl(String url);
    String getUrl(String tinyUrl);
    boolean containsUrl(String url);
    boolean containsTinyUrl(String tinyUrl);
    int size();
    int getIndex();
}
