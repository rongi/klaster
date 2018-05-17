package com.github.rongi.klaster.samples.examples.stateful.articleview

import com.github.rongi.klaster.samples.main.model.Article

data class ArticleViewPresenter(
  private val article: Article
) {

  private var view: ArticleView? = null

  private var checked: Boolean = false

  var onArticleClick: ((article: Article) -> Unit)? = null

  var onArticleDeleteClick: ((article: Article) -> Unit)? = null

  fun bindView(view: ArticleView) {
    this.view = view
    updateView(view)
  }

  fun onArticleClick() {
    onArticleClick?.invoke(article)
  }

  fun onArticleDeleteClick() {
    onArticleDeleteClick?.invoke(article)
  }

  fun onCheckedChanged(checked: Boolean) {
    this.checked = checked
  }

  private fun updateView(view: ArticleView) {
    view.setTitle(article.title)
    view.setChecked(checked)
  }

}