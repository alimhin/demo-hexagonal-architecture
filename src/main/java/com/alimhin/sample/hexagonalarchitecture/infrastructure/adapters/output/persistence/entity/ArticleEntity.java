package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * The type Article entity.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate publicationDate;

    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Draft status.
         */
        DRAFT,
        /**
         * Published status.
         */
        PUBLISHED;
    }

}
