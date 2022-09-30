package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity.ArticleEntity;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.mapper.PersistenceArticleMapper;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArticlePersistenceAdapterTest {

    private static final Long   DEFAULT_ID    = 1L;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String DEFAULT_BODY  = "AAAAA";

    private static final Article.Status DOMAIN_PUBLISHED_STATUS = Article.Status.PUBLISHED;
    private static final ArticleEntity.Status ENTITY_PUBLISHED_STATUS = ArticleEntity.Status.PUBLISHED;

    private Article defaultArticle;
    private ArticleEntity defaultArticleEntity;

    @Mock
    private ArticleRepository repository;
    @Mock
    private PersistenceArticleMapper mapper;

    @InjectMocks
    private ArticlePersistenceAdapter adapter;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        defaultArticle = Article.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(DOMAIN_PUBLISHED_STATUS)
                .publicationDate(LocalDate.now())
                .build();

        defaultArticleEntity = ArticleEntity.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(ENTITY_PUBLISHED_STATUS)
                .publicationDate(LocalDate.now())
                .build();
    }

    @Test
    void save() {
        // GIVEN
        when(this.mapper.toEntity(defaultArticle)).thenReturn(defaultArticleEntity);
        when(this.repository.save(defaultArticleEntity)).thenReturn(defaultArticleEntity);
        when(this.mapper.toDomain(defaultArticleEntity)).thenReturn(defaultArticle);
        // WHEN
        this.adapter.save(defaultArticle);
        // THEN
        verify(this.mapper).toEntity(defaultArticle);
        verify(this.repository).save(defaultArticleEntity);
        verify(this.mapper).toDomain(defaultArticleEntity);
    }

    @Test
    void getArticleById() {
        // GIVEN
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(defaultArticleEntity));
        when(mapper.toDomain(defaultArticleEntity)).thenReturn(defaultArticle);
        // WHEN
        var article = this.adapter.getArticleById(DEFAULT_ID);
        // THEN
        verify(repository).findById(DEFAULT_ID);
        verify(mapper).toDomain(defaultArticleEntity);
        assertThat(article.get()).isEqualTo(defaultArticle);
    }

    @Test
    void getArticleById_notFound() {
        // GIVEN
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());
        // WHEN
        this.adapter.getArticleById(DEFAULT_ID);
        // THEN
        verify(repository).findById(DEFAULT_ID);
        verify(mapper, never()).toDomain(any(ArticleEntity.class));
    }

    @Test
    void findAll() {
        // GIVEN
        var entity = ArticleEntity.builder()
                .id(2L)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(ENTITY_PUBLISHED_STATUS)
                .publicationDate(LocalDate.now())
                .build();
        when(repository.findAll()).thenReturn(List.of(defaultArticleEntity, entity));
        // WHEN
        adapter.findAll();
        // THEN
        verify(repository).findAll();
        verify(mapper, times(2)).toDomain(any(ArticleEntity.class));
    }
}
