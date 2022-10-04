package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence;

import com.alimhin.sample.hexagonalarchitecture.application.port.output.ArticleOutput;
import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.mapper.PersistenceArticleMapper;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Component
public class ArticlePersistenceAdapter implements ArticleOutput {

    private final PersistenceArticleMapper persistenceArticleMapper;
    private final ArticleRepository articleRepository;

    @Override
    public Article save(Article article) {
        var entity = this.persistenceArticleMapper.toEntity(article);
        var saved = this.articleRepository.save(entity);
        return persistenceArticleMapper.toDomain(saved);
    }

    @Override
    public Optional<Article> getById(Long id) {
        return this.articleRepository.findById(id)
                .filter(Objects::nonNull)
                .map(persistenceArticleMapper::toDomain);
    }

    @Override
    public List<Article> findAll() {
        var articles = this.articleRepository.findAll();
        return StreamSupport.stream(articles.spliterator(), false)
                .map(persistenceArticleMapper::toDomain)
                .collect(Collectors.toList());
    }
}
