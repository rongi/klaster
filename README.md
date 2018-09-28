# A RecyclerView adapter builder

Declare RecyclerView adapters by providing three functions (get item count, create view/view hoder and bind view holder) instead of subclassing. You no longer have to spam useless adapter classes each time you need a trivial adapter. It's functional and Kotlin-friendly.

```kotlin
private fun createAdapter() = Stekker.get()
  .itemCount { articles.size }
  .view(R.layout.list_item, layoutInflater)
  .bind { position ->
    val article = articles[position]
    item_text.text = article.title
    itemView.onClick = { presenter.onArticleClick(article) }
  }
  .build()
```
