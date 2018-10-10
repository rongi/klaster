package com.github.rongi.klaster.samples.examples.generic

import com.github.rongi.klaster.samples.main.model.Article

interface GenericExampleView {
  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}