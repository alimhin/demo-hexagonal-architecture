package com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.api.rest;

import com.alimhin.sample.hexagonalarchitecture.application.port.input.article.ManageArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.article.ReadArticle;
import com.alimhin.sample.hexagonalarchitecture.application.port.input.article.WriteArticle;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyDraftException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleAlreadyPublishedException;
import com.alimhin.sample.hexagonalarchitecture.domain.exception.ArticleNotFoundException;
import com.alimhin.sample.hexagonalarchitecture.domain.model.Article;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.api.util.TestUtil;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleRequest;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleResponse;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleResponseLite;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.data.ArticleStatus;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.mapper.ApiArticleMapper;
import com.alimhin.sample.hexagonalarchitecture.infrastructure.adapters.input.api.rest.ArticleApiAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ArticleApiAdapter.class})
@AutoConfigureMockMvc
@EnableWebMvc
class ArticleApiAdapterTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String DEFAULT_BODY = "AAAAA";

    private static final String API_URL = "/api/articles";
    private static final String API_URL_ID = API_URL + "/{id}";

    @MockBean
    private WriteArticle writeArticle;
    @MockBean
    private ReadArticle readArticle;
    @MockBean
    private ManageArticle manageArticle;
    @MockBean
    private ApiArticleMapper apiArticleMapper;

    @Autowired
    private MockMvc articleApiMock;

    private ArticleRequest request;
    private ArticleResponse response;
    private List<ArticleResponseLite> responseLites;
    private Article article;

    @BeforeEach
    void init() {
        request = ArticleRequest.builder()
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .build();
        response = ArticleResponse.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(ArticleStatus.DRAFT)
                .build();
        article = Article.builder()
                .id(DEFAULT_ID)
                .title(DEFAULT_TITLE)
                .body(DEFAULT_BODY)
                .status(Article.Status.DRAFT)
                .build();


    }

    @Test
    void createArticle() throws Exception {
        // GIVEN
        when(apiArticleMapper.toDomain(request)).thenReturn(article);
        when(writeArticle.createArticle(article)).thenReturn(article);
        when(apiArticleMapper.toResponse(article)).thenReturn(response);
        // WHEN

        // THEN
        articleApiMock.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
                .andExpect(jsonPath("$.status").value(ArticleStatus.DRAFT.name()))
                .andExpect(jsonPath("$.publicationDate").doesNotExist());
    }

    @Test
    void createArticle_emptyTitle_badRequest() throws Exception {
        // GIVEN
        request.setTitle(null);
        // THEN
        articleApiMock.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createArticle_emptyBody_badRequest() throws Exception {
        // GIVEN
        request.setBody(null);
        // THEN
        articleApiMock.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateArticle() throws Exception {
        // GIVEN
        when(writeArticle.updateArticle(DEFAULT_ID, DEFAULT_TITLE, DEFAULT_BODY)).thenReturn(article);
        when(apiArticleMapper.toResponse(article)).thenReturn(response);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
                .andExpect(jsonPath("$.status").value(ArticleStatus.DRAFT.name()))
                .andExpect(jsonPath("$.publicationDate").doesNotExist());
    }

    @Test
    void updateArticle_notFound() throws Exception {
        // GIVEN
        when(writeArticle.updateArticle(DEFAULT_ID, DEFAULT_TITLE, DEFAULT_BODY)).thenThrow(new ArticleNotFoundException());
        // THEN
        articleApiMock.perform(put(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateArticle_nullId_badRequest() throws Exception {
        // GIVEN
        request.setTitle(null);
        // THEN
        articleApiMock.perform(put(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateArticle_emptyTitle_badRequest() throws Exception {
        // GIVEN
        request.setTitle(null);
        // THEN
        articleApiMock.perform(put(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateArticle_emptyBody_badRequest() throws Exception {
        // GIVEN
        request.setBody(null);
        // THEN
        articleApiMock.perform(post(API_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticle() throws Exception {
        // GIVEN
        when(readArticle.getArticleById(DEFAULT_ID)).thenReturn(article);
        when(apiArticleMapper.toResponse(article)).thenReturn(response);
        // WHEN

        // THEN
        articleApiMock.perform(get(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(DEFAULT_ID))
                .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
                .andExpect(jsonPath("$.body").value(DEFAULT_BODY))
                .andExpect(jsonPath("$.status").value(ArticleStatus.DRAFT.name()))
                .andExpect(jsonPath("$.publicationDate").doesNotExist());
    }

    @Test
    void getArticle_notFound() throws Exception {
        // GIVEN
        when(readArticle.getArticleById(DEFAULT_ID)).thenThrow(new ArticleNotFoundException());
        // WHEN

        // THEN
        articleApiMock.perform(get(API_URL_ID, DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllArticles() throws Exception {
        // GIVEN
        when(readArticle.findAllArticles()).thenReturn(List.of(
                Article.builder().id(1L).title("title1").status(Article.Status.DRAFT).build(),
                Article.builder().id(2L).title("title2").status(Article.Status.PUBLISHED).build()
        ));
        when(apiArticleMapper.toResponseLite(Article.builder().id(1L).title("title1").status(Article.Status.DRAFT).build()))
                .thenReturn(ArticleResponseLite.builder().id(1L).title("title1").status(ArticleStatus.DRAFT).build());
        when(apiArticleMapper.toResponseLite(Article.builder().id(2L).title("title2").status(Article.Status.PUBLISHED).build()))
                .thenReturn(ArticleResponseLite.builder().id(2L).title("title2").status(ArticleStatus.PUBLISHED).build());
        // WHEN

        // THEN
        articleApiMock.perform(get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItems(1,2)))
                .andExpect(jsonPath("$.[*].title").value(hasItems("title1","title2")))
                .andExpect(jsonPath("$.[*].body").doesNotExist())
                .andExpect(jsonPath("$.[*].status").value(hasItems("DRAFT","PUBLISHED")))
                .andExpect(jsonPath("$.[*].publicationDate").doesNotExist());

        verify(apiArticleMapper, times(2)).toResponseLite(any(Article.class));
    }

    @Test
    void publishArticle() throws Exception {
        // GIVEN
        when(manageArticle.publishArticle(DEFAULT_ID)).thenReturn(Article.Status.PUBLISHED);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/publish", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("PUBLISHED"));
    }

    @Test
    void publishArticle_alreadyPublished() throws Exception {
        // GIVEN
        when(manageArticle.publishArticle(DEFAULT_ID)).thenThrow(ArticleAlreadyPublishedException.class);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/publish", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void publishArticle_notFound() throws Exception {
        // GIVEN
        when(manageArticle.publishArticle(DEFAULT_ID)).thenThrow(ArticleNotFoundException.class);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/publish", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void draftArticle() throws Exception {
        // GIVEN
        when(manageArticle.draftArticle(DEFAULT_ID)).thenReturn(Article.Status.DRAFT);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/draft", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("DRAFT"));
    }

    @Test
    void draftArticle_alreadyDraft() throws Exception {
        // GIVEN
        when(manageArticle.draftArticle(DEFAULT_ID)).thenThrow(ArticleAlreadyDraftException.class);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/draft", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void draftArticle_notFound() throws Exception {
        // GIVEN
        when(manageArticle.draftArticle(DEFAULT_ID)).thenThrow(ArticleNotFoundException.class);
        // WHEN

        // THEN
        articleApiMock.perform(put(API_URL_ID+"/draft", DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}
