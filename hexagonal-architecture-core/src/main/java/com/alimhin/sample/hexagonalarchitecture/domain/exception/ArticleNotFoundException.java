package com.alimhin.sample.hexagonalarchitecture.domain.exception;

/**
 * The type Article not found.
 */
public class ArticleNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Article not found.
     */
    public ArticleNotFoundException() {
        super("Article not found");
    }
}
