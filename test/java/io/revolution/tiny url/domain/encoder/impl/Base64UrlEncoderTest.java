package io.revolut.tinyurl.domain.encoder.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Base64UrlEncoderTest {
    private Base64UrlEncoder base64UrlEncoder;

    @BeforeEach
    public void beforeEach() {
        this.base64UrlEncoder = new Base64UrlEncoder();
    }

    @Test
    public void encode_WhenIndexProvided_ReturnBase64String() {
        // given
        var index = Integer.MAX_VALUE;

        // when
        var base64 = base64UrlEncoder.encode(index);

        // then
        Assertions.assertEquals("bLMuvc", base64);
    }
}
