# A RecyclerView adapter builder

Declare RecyclerView adapters with builder. You no longer need to declare a class each time you need to create a trivial adapter. It's a functional Kotlin-friendly way to declare adapters.

```kotlin
private fun createAdapter() = Klaster.of<Article>()
  .view(R.layout.list_item)
  .bind { article: Article ->
    item_text.text = article.title
    itemView.onClick = { /* handle click */ }
  }
  .layoutInflater(layoutInflater)
  .build()
```
