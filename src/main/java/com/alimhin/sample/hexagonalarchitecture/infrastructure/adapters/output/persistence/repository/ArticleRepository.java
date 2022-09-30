package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.repository;

import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> { }
