package com.alimhin.sample.hexagonalarchitecture.domain.exception;

public class ArticleAlreadyPublishedException extends RuntimeException{

    public ArticleAlreadyPublishedException(){
        super("Article already published");
    }
}
