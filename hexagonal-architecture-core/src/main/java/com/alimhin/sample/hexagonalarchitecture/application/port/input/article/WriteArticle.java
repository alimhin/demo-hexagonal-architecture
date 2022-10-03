package com.alimhin.sample.hexagonalarchitecture.application.port.input.article;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;

/**
 * The interface Write article.
 */
public interface WriteArticle {

    /**
     * Create article article.
     *
     * @param article the article
     * @return the article
     */
    Article createArticle(Article article);


    /**
     * Update article article.
     *
     * @param title the title
     * @param body  the body
     * @return the article
     */
    Article updateArticle(Long id, String title, String body);
}
