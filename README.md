[![](https://jitpack.io/v/rongi/klaster.svg)](https://jitpack.io/#rongi/klaster)

# Declare RecyclerView adapters without boilerplate

With this library

```kotlin
private fun articlesAdapter() = Klaster.get()
  .itemCount { articles.size }
  .view(R.layout.list_item, layoutInflater)
  .bind { position ->
    val article = articles[position]
    item_text.text = article.title
    itemView.onClick = { presenter.onArticleClick(article) }
  }
  .build()
```

The same adapter declared the usual way

```java
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
```

Ever wondered why you need to declare an extra class for each of your adapters when essentially an adapter is just two functions combined together: `onCreateViewHolder()` and `onBindViewHolder()`? Why can't we have something that takes these two functions and construct a proper adapter for us? Well, you can with this library. And with the power of Kotlin Android Extensions, you don't even need to create `ViewHolder` classes anymore.

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

## But what if I need to overload more functions?

With this builder you can "overload" any function you can overload in `RecyclerView.Adapter`.

```kotlin
fun createAdapter(layoutInflater: LayoutInflater) = Klaster.get()
  .itemCount { articles.size }
  .getItemViewType { position -> position % 2 }
  .view { viewType, parent ->
    when (viewType) {
      ITEM_TYPE_1 -> layoutInflater.inflate(R.layout.list_item1, parent, false)
      ITEM_TYPE_2 -> layoutInflater.inflate(R.layout.list_item2, parent, false)
      else -> throw IllegalStateException("Unknown type: $viewType")
    }
  }
  .bind { position ->
    val article = articles[position]
    item_text.text = article.title
  }
  .bind { position, payloads -> }
  .getItemId {  }
  .setHasStableIds {  }
  .onAttachedToRecyclerView {  }
  .onDetachedFromRecyclerView {  }
  .registerAdapterDataObserver {  }
  .unregisterAdapterDataObserver {  }
  .onFailedToRecycleView {  }
  .onViewAttachedToWindow {  }
  .onViewDetachedFromWindow {  }
  .onViewRecycled {  }
  .build()
```

## Functional way to create adapters

This function creates an adapter backed by a simple `List` of items. It returns a `ListPresenter` interface, which is just a single function to change this list.

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

## Create your own extensions

You can tailor the builder for your needs by creating your own, even more elegant APIs using Kotlin extension functions. For example, if you want to create an adapter for a `List` of items that never change, then you may want to have an API like this (notice no `itemCount()` function).

```kotlin
fun createAdapter(articles: List<Article>, layoutInflater: LayoutInflater) = Klaster.get()
  .view(R.layout.list_item, layoutInflater)
  .bind(articles) { article, position ->
    item_text.text = article.title
  }
  .build()
```

You can have it with an extension function like this.

```kotlin
fun <T> KlasterBuilder.bind(items: List<T>, binder: KlasterViewHolder.(item: T, position: Int) -> Unit): KlasterBuilder =
  this.itemCount(items.size)
    .bind { position ->
      val item = items[position]
      binder(item, position)
    }
```

What if you want your adapter to get items from a list that can change? You may want to have an API like this then.

```kotlin
fun createAdapter(articles: () -> List<Article>, layoutInflater: LayoutInflater) = Klaster.get()
  .view(R.layout.list_item, layoutInflater)
  .bind(articles) { article, position ->
    item_text.text = article.title
  }
  .build()
```

You can get that with this extension function.

```kotlin
fun <T> KlasterBuilder.bind(items: () -> List<T>, binder: KlasterViewHolder.(item: T, position: Int) -> Unit): KlasterBuilder =
  this.itemCount { items().size }
    .bind { position ->
      val item = items()[position]
      binder(item, position)
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
    implementation 'com.github.rongi:klaster:0.3.0'
}
```
