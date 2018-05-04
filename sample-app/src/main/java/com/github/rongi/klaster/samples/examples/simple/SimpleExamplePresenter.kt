package com.github.rongi.klaster.samples.examples.simple

import com.github.rongi.klaster.samples.common.toast
import com.github.rongi.klaster.samples.main.data.articles
import com.github.rongi.klaster.samples.main.model.Article

class SimpleExamplePresenter(
  private val view: SimpleExampleView
) {

  fun onViewCreated() {
     view.showArticles(articles)
  }

  fun onArticleClick(article: Article) {
    view.showToast("Click: ${article.title}")
  }

}
