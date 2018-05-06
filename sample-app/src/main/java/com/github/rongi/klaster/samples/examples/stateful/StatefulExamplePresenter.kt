package com.github.rongi.klaster.samples.examples.stateful

import com.github.rongi.klaster.samples.main.data.articles
import com.github.rongi.klaster.samples.main.model.Article

class StatefulExamplePresenter(
  private val view: StatefulExampleView
) {

  fun onViewCreated() {
    view.showArticles(articles)
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

}
