package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseLite {

    Long id;
    String title;
    ArticleStatus status;

}
