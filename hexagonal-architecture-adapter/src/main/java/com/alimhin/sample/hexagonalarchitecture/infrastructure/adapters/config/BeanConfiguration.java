package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.config;

import com.alimhin.sample.hexagonalarchitecture.domain.service.ArticleService;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.notification.ArticleMailNotificationAdapter;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.output.persistence.ArticlePersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ArticleService articleService(ArticlePersistenceAdapter articlePersistenceAdapter, ArticleMailNotificationAdapter articleMailNotificationAdapter){
        return new ArticleService(articlePersistenceAdapter, articleMailNotificationAdapter);
    }

}
