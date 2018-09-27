package com.github.rongi.stekker.samples.examples.simple

import com.github.rongi.stekker.samples.main.model.Article

interface SimpleExampleView {
  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}