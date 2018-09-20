package com.github.rongi.klaster

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.github.rongi.klaster.test.R
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestBuilder {

  private val appContext = InstrumentationRegistry.getTargetContext()

  private val parent = FrameLayout(appContext)

  @Test
  fun createsViewFromResourceId() {
    val adapter = Klaster.of<Article>()
      .view(R.layout.list_item)
      .bind { article: Article ->
        item_text.text = article.title
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article title"
  }

  @Test
  fun createsViewFromFunction() {
    val adapter = Klaster.of<Article>()
      .view {
        TextView(appContext)
      }
      .bind { article: Article ->
        (itemView as TextView).text = article.title
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView as TextView).text assertEquals "article title"
  }

  @Test
  fun createsViewFromResourceIdWithInitFunction() {
    val adapter = Klaster.of<Article>()
      .view(R.layout.list_item) {
        this.item_text.error = "error message"
      }
      .bind { article: Article ->
        item_text.text = article.title
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article title"
    (viewHolder.itemView.item_text as TextView).error assertEquals "error message"
  }

  @Test
  fun createsViewFromFunctionWithParent() {
    val adapter = Klaster.of<Article>()
      .viewWithParent { parent ->
        LayoutInflater.from(appContext).inflate(R.layout.list_item, parent, false)
      }
      .bind { article: Article ->
        item_text.text = article.title
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    viewHolder.itemView.layoutParams assertIs FrameLayout.LayoutParams::class.java
  }

  @Test
  fun bindsView() {
    val adapter = Klaster.of<Article>()
      .view(R.layout.list_item)
      .bind { article: Article ->
        item_text.text = article.title
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article title"
  }

  @Test
  fun bindsViewWithPosition() {
    val adapter = Klaster.of<Article>()
      .view(R.layout.list_item)
      .bind { article, position ->
        item_text.text = "${article.title} ${position + 1}"
      }
      .useLayoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = mutableListOf(Article("article"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView.item_text as TextView).text assertEquals "article 1"
  }

}

infix fun Any.assertIs(clazz: Class<FrameLayout.LayoutParams>) {
  Assert.assertEquals(clazz, this.javaClass)
}

infix fun Any.assertEquals(expected: Any) {
  Assert.assertEquals(expected, this)
}

data class Article(
  val title: String
)