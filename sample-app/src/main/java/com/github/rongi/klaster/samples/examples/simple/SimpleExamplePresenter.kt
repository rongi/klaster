package com.github.rongi.klaster.samples.examples.simple

import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

class SimpleExamplePresenter(
  private val view: SimpleExampleView,
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
