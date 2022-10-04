package com.alimhin.sample.hexagonalarchitecture.application.port.input;

import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;

import java.util.List;

/**
 * The interface Read article.
 */
public interface ReadArticle {

    /**
     * Gets article by id.
     *
     * @param id the id
     * @return the article by id
     */
    Article getArticleById(Long id);


    /**
     * Find all articles list.
     *
     * @return the list
     */
    List<Article> findAllArticles();
}
