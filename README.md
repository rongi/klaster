# A RecyclerView adapter builder


```kotlin
private fun createAdapter() = Klaster.of<Article>()
  .view(R.layout.list_item)
  .bind { article: Article ->
    item_text.text = article.title
    itemView.onClick = { presenter.onArticleClick(article) }
  }
  .layoutInflater(layoutInflater)
  .build()
```
