package com.alimhin.sample.hexagonalarchitecture.domain.event;


import lombok.Builder;

import java.time.LocalDateTime;

public class DraftArticleEvent extends AbstractArticleEvent{

    @Builder
    protected DraftArticleEvent(Long id, String title, LocalDateTime date) {
        super(id, title, date);
    }
}
