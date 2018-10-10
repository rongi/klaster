package com.github.rongi.klaster.samples.examples.customviewholder

import com.github.rongi.klaster.samples.main.model.Article

interface CustomViewHolderExampleView {
  fun showArticles(articles: List<Article>)
  fun showToast(message: String)
}