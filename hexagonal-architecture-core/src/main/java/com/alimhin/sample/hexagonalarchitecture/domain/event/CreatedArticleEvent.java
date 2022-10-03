package com.alimhin.sample.hexagonalarchitecture.domain.event;


import lombok.Builder;

import java.time.LocalDateTime;

public class CreatedArticleEvent extends AbstractArticleEvent{

    @Builder
    protected CreatedArticleEvent(Long id, String title, LocalDateTime date) {
        super(id, title, date);
    }
}
