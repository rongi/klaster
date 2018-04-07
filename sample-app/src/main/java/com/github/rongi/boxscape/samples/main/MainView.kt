package com.github.rongi.boxscape.samples.main

import com.github.rongi.boxscape.samples.main.model.Article

interface MainView {
  fun showArticles(articles: List<Article>)
}