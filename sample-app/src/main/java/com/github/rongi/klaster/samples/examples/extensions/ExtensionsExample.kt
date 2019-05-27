import android.view.LayoutInflater
import com.github.rongi.klaster.Klaster
import com.github.rongi.klaster.KlasterBuilder
import com.github.rongi.klaster.KlasterViewHolder
import com.github.rongi.klaster.samples.R
import com.github.rongi.klaster.samples.main.model.Article
import kotlinx.android.synthetic.main.list_item.*

fun createAdapter(articles: List<Article>, layoutInflater: LayoutInflater) = Klaster.get()
  .view(R.layout.list_item, layoutInflater)
  .bind(articles) { article, _ ->
    item_text.text = article.title
  }
  .build()

fun <T> KlasterBuilder.bind(items: List<T>, binder: KlasterViewHolder.(item: T, position: Int) -> Unit): KlasterBuilder =
  this.itemCount(items.size)
    .bind { position ->
      val item = items[position]
      binder(item, position)
    }

fun createAdapter(articles: () -> List<Article>, layoutInflater: LayoutInflater) = Klaster.get()
  .view(R.layout.list_item, layoutInflater)
  .bind(articles) { article, _ ->
    item_text.text = article.title
  }
  .build()

fun <T> KlasterBuilder.bind(items: () -> List<T>, binder: KlasterViewHolder.(item: T, position: Int) -> Unit): KlasterBuilder =
  this.itemCount { items().size }
    .bind { position ->
      val item = items()[position]
      binder(item, position)
    }
