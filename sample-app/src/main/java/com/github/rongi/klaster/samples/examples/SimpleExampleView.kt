package com.github.rongi.klaster.samples.examples

import com.github.rongi.klaster.samples.main.model.Article

interface SimpleExampleView {
  fun showArticles(articles: List<Article>)
}