package com.alimhin.sample.hexagonalarchitecture.domain.event;


import lombok.Builder;

import java.time.LocalDateTime;

public class PublishedArticleEvent extends AbstractArticleEvent{

    @Builder
    protected PublishedArticleEvent(Long id, String title, LocalDateTime date) {
        super(id, title, date);
    }
}
