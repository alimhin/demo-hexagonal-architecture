package com.alimhin.sample.hexagonalarchitecture.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Long id;
    private String title;
    private String body;
    private LocalDate publicationDate;
    private Status status;

    public enum Status {
        DRAFT, PUBLISHED;
    }

}
