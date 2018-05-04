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
      .layoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = listOf(Article("article title"))

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
      .layoutInflater(LayoutInflater.from(appContext))
      .build()
    adapter.items = listOf(Article("article title"))

    val viewHolder = adapter.createViewHolder(parent, 0).apply {
      adapter.bindViewHolder(this, 0)
    }

    (viewHolder.itemView as TextView).text assertEquals "article title"
  }

}

infix fun Any.assertEquals(expected: Any) {
  Assert.assertEquals(expected, this)
}

data class Article(
  val title: String
)