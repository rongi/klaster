[![](https://jitpack.io/v/rongi/stekker.svg)](https://jitpack.io/#rongi/stekker)

# Declare RecyclerView adapters in functional way

Make RecyclerView adapters without declaring new classes. Provide only necessary stuff. Usually, it's just three functions (get item count, create view/view holder and bind view holder) instead of subclassing. You no longer have to spam useless adapter classes each time you need a trivial adapter. It's functional and Kotlin-friendly.

This library doesn't compromise on flexibility and doesn't hide stuff from you. If it's possible to do something with subclassing, it's possible to do it with this builder also. It's just a more concise way to declare RecyclerView adapters. 

And if you find this API too verbose, you can always tailor it for your needs with Kotlin extension functions.

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
