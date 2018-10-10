package com.github.rongi.klaster.samples.examples.functional

import com.github.rongi.klaster.samples.main.model.Article

interface ListPresenter {
  fun setItems(items: List<Article>)
}