[![](https://jitpack.io/v/rongi/stekker.svg)](https://jitpack.io/#rongi/stekker)

# Declare RecyclerView adapters without boilerplate

Ever wondered why you need to declare an extra class for each of your adapters when essentially an adapter is just two function combined together: `onCreateViewHolder()` and `onBindViewHolder()`? Why can't we have something that takes those two functions and construct a proper adapter for us? Well, you have it with this library. And with power of Kotlin Android extesions you don't need to create `ViewHolder` classes anymore.

This library doesn't compromise on flexibility and doesn't hide stuff from you. If it's possible to do something by declaring a new adapter class, it's possible to do it with this library. It's just a more concise way to declare RecyclerView adapters. 

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
