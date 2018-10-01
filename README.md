[![](https://jitpack.io/v/rongi/stekker.svg)](https://jitpack.io/#rongi/stekker)

# RecyclerView adapter builder

Make RecyclerView adapters without declaring new classes. Provide only necessary stuff. Usually it's just three functions (get item count, create view/view hoder and bind view holder) instead of subclassing. You no longer have to spam useless adapter classes each time you need a trivial adapter. It's functional and Kotlin-friendly.

We don't compromise on flexibility and don't hide stuff from you. If it's possible to do something with subclassing, it's possible to do it with this builder also. It's just a more concise way to declare RecyclerView adapters! If can make your own shortcuts by creating some extension functions for this builder.

Usage
=====

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

Download
========

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.rongi:stekker:0.1.0'
}
```
