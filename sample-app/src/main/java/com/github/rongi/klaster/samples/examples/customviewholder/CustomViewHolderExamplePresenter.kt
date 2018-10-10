package com.github.rongi.klaster.samples.examples.customviewholder

import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

class CustomViewHolderExamplePresenter(
  private val view: CustomViewHolderExampleView,
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
