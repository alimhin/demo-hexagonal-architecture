package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractArticleData {
    @NotNull
    private String title;
    @NotNull
    private String body;
}
