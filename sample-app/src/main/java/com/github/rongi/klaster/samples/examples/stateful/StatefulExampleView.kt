package com.github.rongi.klaster.samples.examples.stateful

import com.github.rongi.klaster.samples.main.model.Article
import kotlin.reflect.KFunction1

interface StatefulExampleView {

  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}