package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.notification;

import com.alimhin.sample.hexagonalarchitecture.domain.event.CreatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.DraftArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.PublishedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.UpdatedArticleEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class NotifyArticleEventAdapterTest {

    private static final Long   DEFAULT_ID    = 1L;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();


    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private NotifyArticleEventAdapter adapter;

    @Captor
    ArgumentCaptor<MimeMessage> messageCaptor;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
    }

    @Test
    void notifyArticleCreated() throws MessagingException, IOException {
        // GIVEN
        var event = CreatedArticleEvent.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .date(DEFAULT_DATE_TIME)
                .build();
        // WHEN
        adapter.notifyArticleCreated(event);
        // THEN
        verify(javaMailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getSubject()).isEqualTo("Article created");
        assertThat(messageCaptor.getValue().getContent()).isEqualTo(String.format("Article created with id `%d` and title `%s` at %s", event.getId(), event.getTitle(), event.getDate()));
    }

    @Test
    void notifyArticleUpdated() throws MessagingException, IOException {
        // GIVEN
        var event = UpdatedArticleEvent.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .date(DEFAULT_DATE_TIME)
                .build();
        // WHEN
        adapter.notifyArticleUpdated(event);
        // THEN
        verify(javaMailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getSubject()).isEqualTo("Article updated");
        assertThat(messageCaptor.getValue().getContent()).isEqualTo(String.format("Article updated with id `%d` and title `%s` at %s", event.getId(), event.getTitle(), event.getDate()));
    }

    @Test
    void notifyArticlePublished() throws MessagingException, IOException {
        // GIVEN
        var event = PublishedArticleEvent.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .date(DEFAULT_DATE_TIME)
                .build();
        // WHEN
        adapter.notifyArticlePublished(event);
        // THEN
        verify(javaMailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getSubject()).isEqualTo("Article published");
        assertThat(messageCaptor.getValue().getContent()).isEqualTo(String.format("Article published with id `%d` and title `%s` at %s", event.getId(), event.getTitle(), event.getDate()));
    }

    @Test
    void notifyArticleDraft() throws MessagingException, IOException {
        // GIVEN
        var event = DraftArticleEvent.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .date(DEFAULT_DATE_TIME)
                .build();
        // WHEN
        adapter.notifyArticleDraft(event);
        // THEN
        verify(javaMailSender).send(messageCaptor.capture());
        assertThat(messageCaptor.getValue().getSubject()).isEqualTo("Article draft");
        assertThat(messageCaptor.getValue().getContent()).isEqualTo(String.format("Article draft with id `%d` and title `%s` at %s", event.getId(), event.getTitle(), event.getDate()));
    }
}
