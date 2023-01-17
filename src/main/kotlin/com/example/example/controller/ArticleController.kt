package com.example.example.controller

import com.example.example.model.Article
import com.example.example.repository.ArticleRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ArticleController(val articleRepository: ArticleRepository) {
    @GetMapping("/articles")
    fun getArticles(): List<Article> = articleRepository.findAll()

    @PostMapping("/articles")
    fun createArticle(@Valid @RequestBody article: Article): Article = articleRepository.save(article)

    @GetMapping("/articles/{id}")
    fun getArticle(@PathVariable id: Long): ResponseEntity<Article> {
        return articleRepository.findById(id).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/article/{id}")
    fun updateArticle(@PathVariable id: Long, @Valid @RequestBody newArticle: Article): ResponseEntity<Article> {
        return articleRepository.findById(id).map { existingArticle ->
            val updatedArticle: Article = existingArticle.copy(title = newArticle.title, content = newArticle.content)
            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }


}
