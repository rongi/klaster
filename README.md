# A RecyclerView adapter builder


```kotlin
val adapter = Klaster.of<Article>()
  .view(R.layout.list_item)
  .bind { article: Article ->
    item_text.text = article.title
  }
  .build()
```
