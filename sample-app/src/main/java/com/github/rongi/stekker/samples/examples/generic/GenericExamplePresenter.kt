package com.github.rongi.stekker.samples.examples.generic

import com.github.rongi.stekker.samples.main.data.ArticlesProvider
import com.github.rongi.stekker.samples.main.model.Article

class GenericExamplePresenter(
  private val view: GenericExampleView,
  private val articlesProvider: ArticlesProvider
) {

  private var articles: List<Article> = emptyList()

  fun onViewCreated() {
    articles = articlesProvider.getArticles()
    view.showArticles(articles.toList())
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

}
