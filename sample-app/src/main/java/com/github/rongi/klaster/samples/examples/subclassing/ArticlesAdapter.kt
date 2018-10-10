package com.github.rongi.klaster.samples.examples.subclassing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.common.onClick
import com.github.rongi.klaster.samples.examples.subclassing.ArticlesAdapter.ArticlesViewHolder
import com.github.rongi.klaster.samples.main.data.ArticlesProvider
import com.github.rongi.klaster.samples.main.model.Article

/**
 * This is not an example of how this library works. This is just for a comparison
 * with a vanilla way to declare adapters.
 */

val articles: List<Article> = ArticlesProvider.getArticles()

private class ArticlesAdapter(
  private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ArticlesViewHolder>() {

  val onItemClick: (() -> Unit)? = null

  override fun getItemCount(): Int {
    return articles.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
    val view = layoutInflater.inflate(R.layout.list_item, parent, false)
    return ArticlesViewHolder(view)
  }

  override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
    val article = articles[position]
    holder.articleTitle.text = article.title
    holder.itemView.onClick = { onItemClick?.invoke() }
  }

  private class ArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val articleTitle: TextView = itemView.findViewById(R.id.item_text)
  }

}

