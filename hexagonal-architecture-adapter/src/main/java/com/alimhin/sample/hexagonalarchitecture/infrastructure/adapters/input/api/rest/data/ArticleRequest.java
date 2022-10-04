package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.data;


import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public class ArticleRequest extends AbstractArticleData implements Serializable {

    @Builder
    protected ArticleRequest(String title, String body) {
        super(title, body);
    }
}
