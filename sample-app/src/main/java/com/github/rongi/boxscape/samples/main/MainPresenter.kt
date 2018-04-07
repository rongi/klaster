package com.github.rongi.boxscape.samples.main

import com.github.rongi.boxscape.samples.main.data.articles

class MainPresenter(
  private val view: MainView
) {

  fun onViewCreated() {
     view.showArticles(articles)
  }

}