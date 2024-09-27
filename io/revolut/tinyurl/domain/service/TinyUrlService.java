package io.revolut.tinyurl.domain.service;

import io.revolut.tinyurl.domain.encoder.IEncoder;
import io.revolut.tinyurl.domain.exceptions.LimitOverflowException;
import io.revolut.tinyurl.domain.exceptions.TinyUrlCollisionException;
import io.revolut.tinyurl.domain.exceptions.UrlExistsException;
import io.revolut.tinyurl.domain.exceptions.UrlNotFoundException;
import io.revolut.tinyurl.repository.IRepository;

public class TinyUrlService {
    private static final int MAX_LIMIT = 100;
    private final IEncoder encoder;
    private final IRepository repository;

    public TinyUrlService(
            IEncoder encoder,
            IRepository repository
            ) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public void create(String url) {
        if(repository.size() > MAX_LIMIT) {
            throw new LimitOverflowException(
                    String.format("Max limit for urls are %s", MAX_LIMIT));
        }

        if(repository.containsUrl(url)) {
            throw new UrlExistsException(
                    String.format("Url %s already exists", url)
            );
        }

        var tinyUrl = encoder.encode(repository.getIndex());

        // when can do retries here from line 34
        if(repository.containsTinyUrl(tinyUrl)) {
            throw new TinyUrlCollisionException(
                    String.format("TinyUrl %s collision exists", tinyUrl)
            );
        }

        this.repository.store(url, tinyUrl);
    }


    public String getTinyUrl(String url) {
        if(!repository.containsUrl(url)) {
            throw new UrlNotFoundException(
                    String.format("Url %s not found", url)
            );
        }
        return repository.getTinyUrl(url);
    }
}
