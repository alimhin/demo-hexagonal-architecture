package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.mapper;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity.ArticleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-03T19:06:55+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.10 (AdoptOpenJDK)"
)
@Component
public class PersistenceArticleMapperImpl implements PersistenceArticleMapper {

    @Override
    public Article toDomain(ArticleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Article.ArticleBuilder article = Article.builder();

        return article.build();
    }

    @Override
    public ArticleEntity toEntity(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleEntity articleEntity = new ArticleEntity();

        return articleEntity;
    }
}
