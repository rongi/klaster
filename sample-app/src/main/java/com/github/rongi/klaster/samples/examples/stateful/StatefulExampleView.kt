package com.github.rongi.klaster.samples.examples.stateful

import com.github.rongi.klaster.samples.main.model.Article

interface StatefulExampleView {

  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
  fun deleteArticle(articleIndex: Int)
}