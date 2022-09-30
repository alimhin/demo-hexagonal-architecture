package com.alimhin.sample.hexagonalarchitecture.domain.service;

import com.alimhin.sample.hexagonalarchitecture.application.port.output.NotifyArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.application.port.output.ArticleOutput;
import com.alimhin.sample.hexagonalarchitecture.domain.event.CreatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.DraftArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.PublishedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.UpdatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyDraftException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyPublishedException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleNotFoundException;
import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticleServiceTest {

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_BODY = "AAAAA";
    private static final String UPDATED_BODY = "BBBBB";

    private static final Article.Status DRAFT_STATUS = Article.Status.DRAFT;
    private static final Article.Status PUBLISHED_STATUS = Article.Status.PUBLISHED;

    private Article defaultArticle;

    @Mock
    ArticleOutput articleOutput;
    @Mock
    NotifyArticleEvent articleNotification;

    @InjectMocks
    private ArticleService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        defaultArticle = Article.builder()
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .build();
    }

    @Test
    void createArticle() {
        // GIVEN
        var argumentCaptor = ArgumentCaptor.forClass(CreatedArticleEvent.class);
        when(articleOutput.save(defaultArticle))
                .thenReturn(Article.builder().id(DEFAULT_ID).title(DEFAULT_TITLE).build());
        // WHEN
        service.createArticle(defaultArticle);
        // THEN
        assertThat(defaultArticle.getStatus()).isEqualTo(DRAFT_STATUS);
        verify(articleOutput).save(defaultArticle);
        verify(articleNotification).notifyArticleCreated(argumentCaptor.capture());
        var event = argumentCaptor.getValue();
        assertThat(event.getId()).isEqualTo(DEFAULT_ID);
        assertThat(event.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(event.getDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void updateArticle() {
        // GIVEN
        var argumentCaptor = ArgumentCaptor.forClass(UpdatedArticleEvent.class);
        defaultArticle.setId(DEFAULT_ID);
        defaultArticle.setStatus(DRAFT_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.of(defaultArticle));
        when(articleOutput.save(defaultArticle))
                .thenReturn(Article.builder().id(DEFAULT_ID).title(UPDATED_TITLE).build());
        // WHEN
        service.updateArticle(1L, UPDATED_TITLE, UPDATED_BODY);
        // THEN
        assertThat(defaultArticle.getStatus()).isEqualTo(DRAFT_STATUS);
        verify(articleOutput).save(defaultArticle);
        verify(articleNotification).notifyArticleUpdated(argumentCaptor.capture());
        var event = argumentCaptor.getValue();
        assertThat(event.getId()).isEqualTo(DEFAULT_ID);
        assertThat(event.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(event.getDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void updateArticle_notFound() {
        // GIVEN
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(null));
        // WHEN
        var thrown = assertThrows(ArticleNotFoundException.class, () -> {
            service.updateArticle(1L, UPDATED_TITLE, UPDATED_BODY);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article not found");
    }

    @Test
    void updateArticle_alreadyPublished() {
        // GIVEN
        defaultArticle.setStatus(PUBLISHED_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(defaultArticle));
        // WHEN
        var thrown = assertThrows(ArticleAlreadyPublishedException.class, () -> {
            service.updateArticle(1L, UPDATED_TITLE, UPDATED_BODY);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article already published");
    }

    @Test
    void publishArticle() {
        // GIVEN
        var argumentCaptor = ArgumentCaptor.forClass(PublishedArticleEvent.class);
        defaultArticle.setId(DEFAULT_ID);
        defaultArticle.setStatus(DRAFT_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.of(defaultArticle));
        when(articleOutput.save(defaultArticle)).thenReturn(defaultArticle);
        // WHEN
        service.publishArticle(DEFAULT_ID);
        // THEN
        assertThat(defaultArticle.getStatus()).isEqualTo(PUBLISHED_STATUS);
        verify(articleNotification).notifyArticlePublished(argumentCaptor.capture());
        var event = argumentCaptor.getValue();
        assertThat(event.getId()).isEqualTo(DEFAULT_ID);
        assertThat(event.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(event.getDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void publishArticle_notFound() {
        // GIVEN
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(null));
        // WHEN
        var thrown = assertThrows(ArticleNotFoundException.class, () -> {
            service.publishArticle(1L);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article not found");
    }

    @Test
    void publishArticle_alreadyPublished() {
        // GIVEN
        defaultArticle.setStatus(PUBLISHED_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(defaultArticle));
        // WHEN
        var thrown = assertThrows(ArticleAlreadyPublishedException.class, () -> {
            service.publishArticle(1L);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article already published");
    }

    @Test
    void draftArticle_notFound() {
        // GIVEN
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(null));
        // WHEN
        var thrown = assertThrows(ArticleNotFoundException.class, () -> {
            service.draftArticle(1L);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article not found");
    }

    @Test
    void draftArticle_alreadyDraft() {
        // GIVEN
        defaultArticle.setStatus(DRAFT_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(defaultArticle));
        // WHEN
        var thrown = assertThrows(ArticleAlreadyDraftException.class, () -> {
            service.draftArticle(1L);
        });
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article already draft");
    }

    @Test
    void draftArticle() {
        // GIVEN
        var argumentCaptor = ArgumentCaptor.forClass(DraftArticleEvent.class);
        defaultArticle.setId(DEFAULT_ID);
        defaultArticle.setStatus(PUBLISHED_STATUS);
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.of(defaultArticle));
        when(articleOutput.save(defaultArticle)).thenReturn(defaultArticle);
        // WHEN
        service.draftArticle(DEFAULT_ID);
        // THEN
        assertThat(defaultArticle.getStatus()).isEqualTo(DRAFT_STATUS);
        verify(articleNotification).notifyArticleDraft(argumentCaptor.capture());
        var event = argumentCaptor.getValue();
        assertThat(event.getId()).isEqualTo(DEFAULT_ID);
        assertThat(event.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(event.getDate()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    void getArticleById() {
        // GIVEN
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.of(defaultArticle));
        // WHEN
        var article = service.getArticleById(1L);
        // THEN
        verify(articleOutput).getArticleById(1L);
    }

    @Test
    void getArticleById_notFound() {
        // GIVEN
        when(articleOutput.getArticleById(DEFAULT_ID)).thenReturn(Optional.ofNullable(null));
        // WHEN
        var thrown = assertThrows(ArticleNotFoundException.class, () -> service.getArticleById(1L));
        // THEN
        assertThat(thrown.getMessage()).isEqualTo("Article not found");
    }

    @Test
    void findAllArticles() {
        // GIVEN
        when(articleOutput.findAll()).thenReturn(List.of());
        // WHEN
        this.service.findAllArticles();
        // THEN
        verify(this.articleOutput).findAll();
    }
}
