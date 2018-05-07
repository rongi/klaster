package com.github.rongi.klaster.samples.examples.stateful

import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

class StatefulExamplePresenter(
  private val view: StatefulExampleView,
  private val articlesProvider: ArticlesProvider
) {

  private var articles: List<Article> = emptyList()

  fun onViewCreated() {
    articles = articlesProvider.getArticles()
    view.showArticles(articles)
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

  fun onArticleDeleteClick(article: Article) {
    val articleIndex = articles.indexOfFirst { it.id == article.id }
    if (articleIndex != -1) {
      articles = articles.filterIndexed { index, _ -> index != articleIndex }
      view.deleteArticle(articleIndex)
    }
  }

}
