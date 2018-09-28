package com.github.rongi.stekker.samples.examples.`fun`

import com.github.rongi.stekker.samples.main.data.ArticlesProvider
import com.github.rongi.stekker.samples.main.model.Article

class FunExamplePresenter(
  private val view: FunExampleView,
  private val articlesProvider: ArticlesProvider,
  private val listPresenter: ListPresenter
) {

  private var articles: List<Article> = emptyList()

  fun onViewCreated() {
    articles = articlesProvider.getArticles()
    listPresenter.setItems(articles.toList())
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

}
