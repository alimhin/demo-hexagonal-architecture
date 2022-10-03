package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractArticleDataWithId extends AbstractArticleData {
    private Long id;

    protected AbstractArticleDataWithId(Long id, String title, String body) {
        super(title, body);
        this.id = id;
    }
}
