# A RecyclerView adapter builder

Declare RecyclerView adapters with builder. You no longer need to declare a class each time you need to create a trivial adapter. It's a functional, Kotlin-friendly way to declare adapters.

```kotlin
private var articles: List<Article> = emptyList()

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
