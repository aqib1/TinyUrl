package io.revolut.tinyurl.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TinyUrlRepositoryTest {
    private TinyUrlRepository repository;

    @BeforeEach
    public void beforeEach() {
        this.repository = new TinyUrlRepository();
    }

    @Test
    public void store_WhenUrlAndTinyUrlProvided_ShouldStore() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var expected = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(url, expected);

        // then
        var actual = repository.getTinyUrl(url);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getUrl_WhenTinyUrlProvided_ReturnActualUrl() {
        // Given
        var expected = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(expected, tinyUrl);

        // then
        var actual = this.repository.getUrl(tinyUrl);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getUrl_WhenTinyUrlNotExists_ReturnNull() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";


        // then
        var actual = this.repository.getTinyUrl(url);
        Assertions.assertNull(actual);
    }

    @Test
    public void getTinyUrl_WhenUrlNotExists_ReturnNull() {
        // Given
        var tinyUrl = "https://tinyurl.com/cgHfC1";


        // then
        var actual = this.repository.getUrl(tinyUrl);
        Assertions.assertNull(actual);
    }

    @Test
    public void size_ShouldReturnTheSizeOfRepositoryData() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(url, tinyUrl);

        // then
        Assertions.assertEquals(1, this.repository.size());
    }

    @Test
    public void containsUrl_WhenUrlAlreadyExists_ReturnTrue() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(url, tinyUrl);

        // then
        Assertions.assertTrue(this.repository.containsUrl(url));
    }

    @Test
    public void containsTinyUrl_WhenTinyUrlAlreadyExists_ReturnTrue() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(url, tinyUrl);

        // then
        Assertions.assertTrue(this.repository.containsTinyUrl(tinyUrl));
    }

    @Test
    public void getIndex_ReturnUpdatedIndexWhenUrlStored() {
        // Given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        this.repository.store(url, tinyUrl);

        // then
        Assertions.assertEquals(1, this.repository.getIndex());
    }
}
