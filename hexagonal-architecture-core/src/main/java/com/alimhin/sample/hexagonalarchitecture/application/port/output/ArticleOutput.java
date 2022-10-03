package com.alimhin.sample.hexagonalarchitecture.application.port.output;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;

import java.util.List;
import java.util.Optional;

/**
 * The interface Article output.
 */
public interface ArticleOutput{

    /**
     * Save article.
     *
     * @param object the object
     * @return the article
     */
    Article save(Article object);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Optional<Article> getById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Article> findAll();
}
