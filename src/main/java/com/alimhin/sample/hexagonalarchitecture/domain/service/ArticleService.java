package com.alimhin.sample.hexagonalarchitecture.domain.service;

import com.alimhin.sample.hexagonalarchitecture.application.port.input.ManageArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.ReadArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.WriteArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.output.NotifyArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.application.port.output.ArticleOutput;
import com.alimhin.sample.hexagonalarchitecture.domain.event.CreatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.DraftArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.PublishedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.event.UpdatedArticleEvent;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyDraftException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyPublishedException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleNotFoundException;
import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ArticleService implements WriteArticle, ReadArticle, ManageArticle {

    private final ArticleOutput articleOutput;
    private final NotifyArticleEvent articleNotification;

    @Override
    public Article createArticle(Article article) {
        article.setStatus(Article.Status.DRAFT);
        var created = this.articleOutput.save(article);
        var event = CreatedArticleEvent.builder()
                .id(created.getId())
                .title(created.getTitle())
                .date(LocalDateTime.now())
                .build();
        this.articleNotification.notifyArticleCreated(event);
        return created;
    }

    @Override
    public Article updateArticle(Long id, String title, String body) {
        var article = this.articleOutput.getArticleById(id).orElseThrow(ArticleNotFoundException::new);
        if(Article.Status.PUBLISHED == article.getStatus()){
            throw new ArticleAlreadyPublishedException();
        }
        article.setTitle(title);
        article.setBody(body);
        var updated = this.articleOutput.save(article);
        var event = UpdatedArticleEvent.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .date(LocalDateTime.now())
                .build();
        this.articleNotification.notifyArticleUpdated(event);
        return updated;
    }

    @Override
    public Article.Status publishArticle(Long id) {
        var article = this.articleOutput.getArticleById(id).orElseThrow(ArticleNotFoundException::new);
        if(Article.Status.PUBLISHED == article.getStatus()){
            throw new ArticleAlreadyPublishedException();
        }
        article.setStatus(Article.Status.PUBLISHED);
        var published = this.articleOutput.save(article);
        var event = PublishedArticleEvent.builder()
                .id(published.getId())
                .title(published.getTitle())
                .date(LocalDateTime.now())
                .build();
        this.articleNotification.notifyArticlePublished(event);
        return published.getStatus();
    }

    @Override
    public Article.Status draftArticle(Long id) {
        var article = this.articleOutput.getArticleById(id).orElseThrow(ArticleNotFoundException::new);
        if(Article.Status.DRAFT == article.getStatus()){
            throw new ArticleAlreadyDraftException();
        }
        article.setStatus(Article.Status.DRAFT);
        var draft = this.articleOutput.save(article);
        var event = DraftArticleEvent.builder()
                .id(draft.getId())
                .title(draft.getTitle())
                .date(LocalDateTime.now())
                .build();
        this.articleNotification.notifyArticleDraft(event);
        return draft.getStatus();
    }

    @Override
    public Article getArticleById(Long id) {
        return this.articleOutput.getArticleById(id).orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public List<Article> findAllArticles() {
        return this.articleOutput.findAll();
    }
}
