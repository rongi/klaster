[![](https://jitpack.io/v/rongi/klaster.svg)](https://jitpack.io/#rongi/klaster)

# Declare RecyclerView adapters without boilerplate

Ever wondered why you need to declare an extra class for each of your adapters when essentially an adapter is just two function combined together: `onCreateViewHolder()` and `onBindViewHolder()`? Why can't we have something that takes those two functions and construct a proper adapter for us? Well, you can with this library. And with power of Kotlin Android Extesions you don't even need to create `ViewHolder` classes anymore.

This library doesn't compromise on flexibility and doesn't hide stuff from you. If it's possible to do something by declaring a new adapter class, it's possible to do it with this library. It's just a more concise way to declare RecyclerView adapters. 

You may find that for your specific case you would prefer to have even more concise API. Well, with Kotlin extension functions you can tailor this API for your needs.

Usage
=====

## Basic

```kotlin
private fun createAdapter() = Klaster.get()
  .itemCount { articles.size }
  .view(R.layout.list_item, layoutInflater)
  .bind { position ->
    val article = articles[position]
    item_text.text = article.title
    itemView.onClick = { presenter.onArticleClick(article) }
  }
  .build()
```

## With a custom `ViewHolder`

```kotlin
private fun createAdapter() = Klaster.withViewHolder<MyViewHolder>()
  .itemCount { articles.size }
  .viewHolder { _, parent ->
    val view = layoutInflater.inflate(R.layout.list_item, parent, false)
    MyViewHolder(view)
  }
  .bind { position ->
    val article = articles[position]
    articleTitle.text = article.title
    itemView.onClick = { presenter.onArticleClick(article) }
  }
  .build()
```

## Functional way to create adapters

This function creates an adapter that shows a list of items and returns a `ListPresenter` interface, which is just a single function to change this list.

```kotlin
interface ListPresenter {
  fun setItems(items: List<Article>)
}

private fun createAdapter(
  layoutInflater: LayoutInflater,
  onItemClick: (Article) -> Unit
): Pair<RecyclerView.Adapter<*>, ListPresenter> {
  var articles: List<Article> = emptyList()

  val adapter = Klaster.get()
    .itemCount { articles.size }
    .view(R.layout.list_item, layoutInflater)
    .bind { position ->
      val article = articles[position]
      item_text.text = article.title
      itemView.onClick = { onItemClick(article) }
    }
    .build()

  val presenter = object : ListPresenter {
    override fun setItems(items: List<Article>) {
      articles = items
      adapter.notifyDataSetChanged()
    }
  }

  return adapter to presenter
}
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
    implementation 'com.github.rongi.klaster:klaster:0.3.0'
}
```
