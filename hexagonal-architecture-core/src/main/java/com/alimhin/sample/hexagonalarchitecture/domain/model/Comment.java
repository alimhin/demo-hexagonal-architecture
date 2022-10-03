package com.alimhin.sample.hexagonalarchitecture.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;
    private String body;
    private Long articleId;
    private LocalDateTime date;

}
