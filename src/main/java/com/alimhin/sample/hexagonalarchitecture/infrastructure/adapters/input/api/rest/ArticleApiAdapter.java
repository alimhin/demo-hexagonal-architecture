package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest;

import com.alimhin.sample.hexagonalarchitecture.application.port.input.ManageArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.ReadArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.WriteArticle;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyDraftException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyPublishedException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleNotFoundException;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleRequest;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleResponse;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleResponseLite;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.mapper.ApiArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleApiAdapter {

    private final WriteArticle writeArticle;
    private final ReadArticle readArticle;
    private final ManageArticle manageArticle;
    private final ApiArticleMapper apiArticleMapper;


    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleRequest request) throws URISyntaxException {
        var articleRequest = this.apiArticleMapper.toDomain(request);
        var response = this.writeArticle.createArticle(articleRequest);
        var created = this.apiArticleMapper.toResponse(response);
        return ResponseEntity.created(new URI("/api/articles/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable("id") Long id, @RequestBody @Valid ArticleRequest request){
        try {
            var response = this.writeArticle.updateArticle(id, request.getTitle(), request.getBody());
            var updated = this.apiArticleMapper.toResponse(response);
            return ResponseEntity.ok().body(updated);
        } catch(ArticleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        try {
            var response = this.readArticle.getArticleById(id);
            return ResponseEntity.ok().body(this.apiArticleMapper.toResponse(response));
        } catch(ArticleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseLite>> getArticles() {
        var response = this.readArticle.findAllArticles();
        return ResponseEntity.ok().body(response.stream().map(apiArticleMapper::toResponseLite).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<String> publishArticle(@PathVariable("id") Long id){
        try {
            var response = this.manageArticle.publishArticle(id);
            return ResponseEntity.ok().body(response.name());
        } catch(ArticleAlreadyPublishedException e) {
            return ResponseEntity.badRequest().build();
        } catch(ArticleNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/draft")
    public ResponseEntity<String> draftArticle(@PathVariable("id") Long id){
        try {
            var response = this.manageArticle.draftArticle(id);
            return ResponseEntity.ok().body(response.name());
        } catch(ArticleAlreadyDraftException e) {
            return ResponseEntity.badRequest().build();
        } catch(ArticleNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
