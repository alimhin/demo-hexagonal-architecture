package com.alimhin.sample.hexagonalarchitecture.application.port.output;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;

import java.util.List;
import java.util.Optional;

/**
 * The interface Article output.
 */
public interface ArticleOutput {

    /**
     * Save article.
     *
     * @param article the article
     * @return the article
     */
    Article save(Article article);

    /**
     * Gets article by id.
     *
     * @param id the id
     * @return the article by id
     */
    Optional<Article> getArticleById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Article> findAll();
}
