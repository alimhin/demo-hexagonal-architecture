package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponse extends AbstractArticleDataWithId{

    private ArticleStatus status;
    private LocalDate publicationDate;

    @Builder
    protected ArticleResponse(Long id, String title, String body, ArticleStatus status, LocalDate publicationDate) {
        super(id, title, body);
        this.status = status;
        this.publicationDate = publicationDate;
    }
}
