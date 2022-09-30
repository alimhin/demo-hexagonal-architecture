package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.mapper;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleRequest;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ApiArticleMapperTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String DEFAULT_BODY = "AAAAA";
    private static final Article.Status DOMAIN_STATUS = Article.Status.PUBLISHED;
    private static final ArticleStatus API_STATUS = ArticleStatus.PUBLISHED;
    private static final LocalDate DEFAULT_DATE = LocalDate.now();

    private ApiArticleMapperImpl mapper = new ApiArticleMapperImpl();

    @Test
    void toDomain_null() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toDomain() {
        // GIVEN
        var request = ArticleRequest.builder()
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .build();
        // WHEN
        var article = mapper.toDomain(request);
        // THEN
        assertThat(article.getId()).isNull();
        assertThat(article.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(article.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(article.getStatus()).isNull();
        assertThat(article.getPublicationDate()).isNull();
    }

    @Test
    void toResponseLite_null() {
        assertThat(mapper.toResponseLite(null)).isNull();
    }

    @Test
    void toResponseLite() {
        // GIVEN
        var article = Article.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .status(DOMAIN_STATUS)
                .build();
        // WHEN
        var lite = this.mapper.toResponseLite(article);
        // THEN
        assertThat(lite.getId()).isEqualTo(DEFAULT_ID);
        assertThat(lite.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(lite.getStatus()).isEqualTo(API_STATUS);
    }

    @Test
    void toResponse_null() {
        assertThat(mapper.toResponse(null)).isNull();
    }

    @Test
    void toResponse() {
        // GIVEN
        var article = Article.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(DOMAIN_STATUS)
                .publicationDate(DEFAULT_DATE)
                .build();
        // WHEN
        var response = this.mapper.toResponse(article);
        // THEN
        assertThat(response.getId()).isEqualTo(DEFAULT_ID);
        assertThat(response.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(response.getBody()).isEqualTo(DEFAULT_BODY);
        assertThat(response.getStatus()).isEqualTo(API_STATUS);
        assertThat(article.getPublicationDate()).isEqualTo(DEFAULT_DATE);
    }
}
