package com.alimhin.sample.hexagonalarchitecture.domain.exception;

public class ArticleAlreadyDraftException extends RuntimeException{

    public ArticleAlreadyDraftException(){
        super("Article already draft");
    }
}
