package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.mapper;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity.ArticleEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for the domain {@link Article} and its entity {@link ArticleEntity}.
 */
@Mapper(componentModel = "spring")
public interface PersistenceArticleMapper {

    /**
     * To domain article.
     *
     * @param entity the entity
     * @return the article
     */
    Article toDomain(ArticleEntity entity);

    /**
     * To entity article entity.
     *
     * @param article the article
     * @return the article entity
     */
    ArticleEntity toEntity(Article article);

}
