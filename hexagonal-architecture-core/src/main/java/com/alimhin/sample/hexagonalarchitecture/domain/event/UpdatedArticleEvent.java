package com.alimhin.sample.hexagonalarchitecture.domain.event;


import lombok.Builder;

import java.time.LocalDateTime;

public class UpdatedArticleEvent extends AbstractArticleEvent{

    @Builder
    protected UpdatedArticleEvent(Long id, String title, LocalDateTime date) {
        super(id, title, date);
    }
}
