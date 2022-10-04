package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.mapper;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.data.ArticleRequest;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.data.ArticleResponse;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.data.ArticleResponseLite;
import org.mapstruct.Mapper;

/**
 * Mapper for the com.alimhin.sample.hexagonalarchitecture.domain {@link Article} and its api request and responses {@link ArticleRequest} {@link ArticleResponseLite} {@link ArticleResponse}.
 */
@Mapper( componentModel = "spring")
public interface ApiArticleMapper {

    /**
     * To com.alimhin.sample.hexagonalarchitecture.domain article.
     *
     * @param request the request
     * @return the article
     */
    Article toDomain(ArticleRequest request);

    /**
     * To response lite article response lite.
     *
     * @param domain the com.alimhin.sample.hexagonalarchitecture.domain
     * @return the article response lite
     */
    ArticleResponseLite toResponseLite(Article domain);

    /**
     * To response article response.
     *
     * @param domain the com.alimhin.sample.hexagonalarchitecture.domain
     * @return the article response
     */
    ArticleResponse toResponse(Article domain);
}
