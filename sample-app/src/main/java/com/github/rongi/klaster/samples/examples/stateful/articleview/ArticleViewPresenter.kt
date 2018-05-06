package com.github.rongi.klaster.samples.examples.stateful.articleview

import com.github.rongi.klaster.samples.main.model.Article

data class ArticleViewPresenter(
  private val article: Article
) {

  private var view: ArticleView? = null

  var onArticleClickListener: ((article: Article) -> Unit)? = null

  fun setView(view: ArticleView) {
    this.view = view
    updateView(view)
  }

  fun onArticleClick() {
    onArticleClickListener?.invoke(article)
  }

  private fun updateView(view: ArticleView) {
    view.setTitle(article.title)
  }

}