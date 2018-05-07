package com.github.rongi.klaster.samples.examples.stateful.articleview

import com.github.rongi.klaster.samples.main.model.Article

data class ArticleViewPresenter(
  private val article: Article
) {

  private var view: ArticleView? = null

  var onArticleClick: ((article: Article) -> Unit)? = null

  var onArticleDeleteClick: ((article: Article) -> Unit)? = null

  fun setView(view: ArticleView) {
    this.view = view
    updateView(view)
  }

  fun onArticleClick() {
    onArticleClick?.invoke(article)
  }

  fun onArticleDeleteClick() {
    onArticleDeleteClick?.invoke(article)
  }

  private fun updateView(view: ArticleView) {
    view.setTitle(article.title)
  }

}