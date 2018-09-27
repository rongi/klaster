package com.github.rongi.stekker.samples.examples.generic

import com.github.rongi.stekker.samples.main.model.Article

interface GenericExampleView {
  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}