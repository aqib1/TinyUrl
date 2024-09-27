package io.revolut.tinyurl.domain.service;

import io.revolut.tinyurl.domain.encoder.IEncoder;
import io.revolut.tinyurl.domain.exceptions.LimitOverflowException;
import io.revolut.tinyurl.domain.exceptions.TinyUrlCollisionException;
import io.revolut.tinyurl.domain.exceptions.UrlExistsException;
import io.revolut.tinyurl.domain.exceptions.UrlNotFoundException;
import io.revolut.tinyurl.repository.IRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TinyUrlServiceTest {
    private static final int MAX_LIMIT = 100;
    private final IEncoder encoder = mock(IEncoder.class);
    private final IRepository repository = mock(IRepository.class);
    private TinyUrlService tinyUrlService;

    @BeforeEach
    public void beforeEach() {
        this.tinyUrlService = new TinyUrlService(encoder, repository);
    }


    @Test
    public void create_WhenUrlProvided_ReturnTinyUrl() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var expected = "https://tinyurl.com/cgHfC1";

        // when
        doNothing().when(repository).store(anyString(), anyString());
        when(repository.getIndex()).thenReturn(1);
        when(encoder.encode(anyInt())).thenReturn(expected);
        tinyUrlService.create(url);

        // then
        verify(repository, times(1)).store(anyString(), anyString());
        verify(repository, times(1)).size();
        verify(encoder, times(1)).encode(anyInt());
    }

    @Test
    public void create_WhenLimitExceed_ThrowLimitOverflowException() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";

        // when
        when(repository.size()).thenReturn(MAX_LIMIT + 1);

        // then
        Assertions.assertThrows(
                LimitOverflowException.class,
                () -> tinyUrlService.create(url)
        );
        verify(repository, times(1)).size();
    }

    @Test
    public void create_WhenUrlAlreadyExists_ThrowUrlExistsException() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";

        // when
        when(repository.containsUrl(anyString())).thenReturn(true);

        // then
        Assertions.assertThrows(
                UrlExistsException.class,
                () -> tinyUrlService.create(url)
        );
        verify(repository, times(1)).size();
    }

    @Test
    public void create_WhenTinyUrlCollisionExists_ThrowTinyUrlCollisionException() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var tinyUrl = "https://tinyurl.com/cgHfC1";

        // when
        when(encoder.encode(anyInt())).thenReturn(tinyUrl);
        when(repository.containsTinyUrl(anyString())).thenReturn(true);

        // then
        Assertions.assertThrows(
                TinyUrlCollisionException.class,
                () -> tinyUrlService.create(url)
        );
    }

    @Test
    public void getTinyUrl_WhenProvidedUrlExists_ReturnTinyUrl() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";
        var expected = "https://tinyurl.com/cgHfC1";

        // when
        when(repository.containsUrl(anyString())).thenReturn(true);
        when(repository.getTinyUrl(anyString())).thenReturn(expected);

        // then
        var actual = tinyUrlService.getTinyUrl(url);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getTinyUrl_WhenProvidedUrlNotExists_ThrowUrlNotFoundException() {
        // given
        var url = "https://stackoverflow.com/questions/33586244/python-unable-to-convert-input-to-int";

        // when
        when(repository.containsUrl(anyString())).thenReturn(false);

        // then
        Assertions.assertThrows(
                UrlNotFoundException.class,
                () -> tinyUrlService.getTinyUrl(url)
        );
    }
}
