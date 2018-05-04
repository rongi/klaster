package com.github.rongi.klaster.samples.examples.simple

import com.github.rongi.klaster.samples.main.model.Article

interface SimpleExampleView {
  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}