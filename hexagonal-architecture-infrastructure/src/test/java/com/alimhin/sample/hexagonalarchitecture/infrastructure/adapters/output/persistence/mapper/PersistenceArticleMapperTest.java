package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.mapper;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity.ArticleEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


class PersistenceArticleMapperTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String DEFAULT_BODY = "AAAAA";
    private static final Article.Status DOMAIN_STATUS = Article.Status.PUBLISHED;
    private static final ArticleEntity.Status ENTITY_STATUS = ArticleEntity.Status.PUBLISHED;
    private static final LocalDate DEFAULT_DATE = LocalDate.now();

    private PersistenceArticleMapperImpl mapper= new PersistenceArticleMapperImpl();

    @Test
    void toDomain_null() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toDomain() {
        // GIVEN
        var entity = ArticleEntity.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(ENTITY_STATUS)
                .publicationDate(DEFAULT_DATE)
                .build();
        // WHEN
        var domain = this.mapper.toDomain(entity);
        // THEN
        assertThat(domain.getId()).isEqualTo(DEFAULT_ID);
        assertThat(domain.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(domain.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(domain.getStatus()).isEqualTo(DOMAIN_STATUS);
        assertThat(domain.getPublicationDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    void toEntity_null() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toEntity() {
        // GIVEN
        var domain = Article.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(DOMAIN_STATUS)
                .publicationDate(DEFAULT_DATE)
                .build();
        // WHEN
        var entity = this.mapper.toEntity(domain);
        // THEN
        assertThat(entity.getId()).isEqualTo(DEFAULT_ID);
        assertThat(entity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(entity.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(entity.getStatus()).isEqualTo(ENTITY_STATUS);
        assertThat(entity.getPublicationDate()).isEqualTo(DEFAULT_DATE);
    }
}
