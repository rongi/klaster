package com.github.rongi.klaster.samples.examples.multipleviewtypes

import com.github.rongi.klaster.samples.main.model.Article

sealed class ListItemViewData

data class HeaderViewData(
  val headerText: String
): ListItemViewData()

data class ArticleViewData(
  val article: Article
): ListItemViewData()

interface MultipleViewTypesExampleView {
  fun showListItems(listItems: List<ListItemViewData>)
  fun showToast(message: String)
}
