package com.github.rongi.klaster.samples.examples.multipleviewtypes

import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

class MultipleViewTypesExamplePresenter(
  private val view: MultipleViewTypesExampleView,
  private val articlesProvider: ArticlesProvider
) {

  private var articles: List<Article> = emptyList()

  fun onViewCreated() {
    articles = articlesProvider.getArticles()

    val articlesAsListItems = articles.map(::ArticleViewData)

    val listItems =
      listOf(HeaderViewData(headerText = "Today")) +
        articlesAsListItems.subList(0, 2) +
        listOf(HeaderViewData(headerText = "Yesterday")) +
        articlesAsListItems.subList(2, articlesAsListItems.size)

    view.showListItems(listItems)
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

}
