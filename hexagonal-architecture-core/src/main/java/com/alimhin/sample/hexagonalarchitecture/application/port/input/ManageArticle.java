package com.alimhin.sample.hexagonalarchitecture.application.port.input;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;

/**
 * The interface Manage article.
 */
public interface ManageArticle {

    /**
     * Publish article article . status.
     *
     * @param id the id
     * @return the article . status
     */
    Article.Status publishArticle(Long id);

    /**
     * Draft article article . status.
     *
     * @param id the id
     * @return the article . status
     */
    Article.Status draftArticle(Long id);
}
