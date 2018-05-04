package com.github.rongi.klaster.samples.examples

import com.github.rongi.klaster.samples.main.data.articles

class SimpleExamplePresenter(
  private val view: SimpleExampleView
) {

  fun onViewCreated() {
     view.showArticles(articles)
  }

}
