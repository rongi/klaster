package com.github.rongi.klaster.samples.examples.functional

import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

class FunctionalExamplePresenter(
  private val view: FunctionalExampleView,
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
