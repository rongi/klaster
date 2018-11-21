[![](https://jitpack.io/v/rongi/klaster.svg)](https://jitpack.io/#rongi/klaster)

# Declare RecyclerView adapters without boilerplate

With this library:

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

The same adapter declared by subclassing:

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

This library doesn't compromise on flexibility and doesn't hide stuff from you. If it's possible to do something by declaring a new adapter class, it's possible to do it with this library also. It's just a more concise way to declare RecyclerView adapters. 

If you ever feel like this adapter builder is too verbose, well, with Kotlin extension functions you can tailor it for your needs. Or you can build more complex things on top of it. Again, just with extension functions.

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

This is how it can look like inside an `Activity` implemented with MVP.

```kotlin
class SimpleExampleActivity : AppCompatActivity(), SimpleExampleView {

  private lateinit var adapter: RecyclerView.Adapter<*>

  private lateinit var presenter: SimpleExamplePresenter

  private var articles: List<Article> = emptyList()

  override fun onCreate(...) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.recycler_view_activity)
    recycler_view.init(this)

    adapter = createAdapter()

    recycler_view.adapter = adapter

    presenter = SimpleExamplePresenter(view = this)
    presenter.onViewCreated()
  }

  override fun showArticles(articles: List<Article>) {
    this.articles = articles
    adapter.notifyDataSetChanged()
  }

  private fun createAdapter() = Klaster.get()
    .itemCount { articles.size }
    .view(R.layout.list_item, layoutInflater)
    .bind { position ->
      val article = articles[position]
      item_text.text = article.title
      itemView.onClick = { presenter.onArticleClick(article) }
    }
    .build()

}
```

## Multiple view types

```kotlin
class MultipleViewTypesExampleActivity : AppCompatActivity(), MultipleViewTypesExampleView {
  // ...

  private fun createAdapter() = Klaster.get()
    .itemCount { listItems.size }
    .getItemViewType { position ->
      when (listItems[position]) {
        is ArticleViewData -> 0
        is HeaderViewData -> 1
      }
    }
    .view { viewType, parent ->
      when (viewType) {
        0 -> layoutInflater.inflate(R.layout.list_item, parent, false)
        1 -> layoutInflater.inflate(R.layout.header, parent, false)
        else -> throw IllegalStateException("Unknown view type: $viewType")
      }
    }
    .bind { position ->
      val listItem = listItems[position]

      when (listItem) {
        is ArticleViewData -> {
          item_text.text = listItem.article.title
          itemView.onClick = { presenter.onArticleClick(listItem.article) }
        }
        is HeaderViewData -> {
          header_text.text = listItem.headerText
        }
      }
    }
    .build()

  private var listItems: List<ListItemViewData> = emptyList()

  override fun showListItems(listItems: List<ListItemViewData>) {
    this.listItems = listItems
    adapter.notifyDataSetChanged()
  }

  sealed class ListItemViewData

  data class HeaderViewData(
    val headerText: String
  ): ListItemViewData()

  data class ArticleViewData(
    val article: Article
  ): ListItemViewData()

}
```

Full example is a part of library's sample app and can be found [here](https://github.com/rongi/klaster/tree/master/sample-app/src/main/java/com/github/rongi/klaster/samples/examples/multipleviewtypes).

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

With this builder, you can "overload" any function you want that can be overloaded by traditional subclassing of `RecyclerView.Adapter`.

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

But does list of items really belong to the `Activity`? Can I achieve better separation of concerns using this library? Yes, and here is an example of how it can be done in a clean and beautiful functional way without subclassing.

The function defined below, `createAdapter()`, creates an adapter backed by a simple `List` of items. This function returns two things:

1. A `RecyclerView.Adapter`, which you can give to your `RecyclerView`.
2. A `ListViewPresenter` interface. This interface you can use to update contents of your adapter, it has a single method that replaces all the items in the adapter with the new ones. You can even pass this presenter into your main presenter as a dependency.

```kotlin
private fun createAdapter(
  layoutInflater: LayoutInflater,
  onItemClick: (Article) -> Unit
): Pair<RecyclerView.Adapter<*>, ListViewPresenter> {
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

  val listViewPresenter = object : ListViewPresenter {
    override fun setItems(items: List<Article>) {
      articles = items
      adapter.notifyDataSetChanged()
    }
  }

  return adapter to listViewPresenter
}

interface ListViewPresenter {
  fun setItems(items: List<Article>)
}
```

Why is this preferred over inheritance? Because it's simpler (less interweaved) and you can achieve a better separation of concerns this way. For example `ListViewPresenter` implementation can be extracted from this function and reused for all other cases where the adapter is backed by a `List`.

## Create your own extensions

You can tailor the builder for your needs by creating your own, even more elegant APIs using Kotlin extension functions. For example, if you want to create an adapter for a `List` of items that never change, then you may want to have a builder that can do things like this (notice no `itemCount()` function).

```kotlin
fun createAdapter(articles: List<Article>, layoutInflater: LayoutInflater) = Klaster.get()
  .view(R.layout.list_item, layoutInflater)
  .bind(articles) { article, position ->
    item_text.text = article.title
  }
  .build()
```

You can achieve it with this extension function.

```kotlin
fun <T> KlasterBuilder.bind(items: List<T>, binder: KlasterViewHolder.(item: T, position: Int) -> Unit): KlasterBuilder =
  this.itemCount(items.size)
    .bind { position ->
      val item = items[position]
      binder(item, position)
    }
```

What if you want your adapter to get items from a list that can change? You may want to have a builder that can do this then.

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
    implementation 'com.github.rongi:klaster:0.3.3'
}
```

[![Klaster](https://github.com/rongi/klaster/raw/master/docs/61X1ix54nPL.jpg)](#Klaster)
