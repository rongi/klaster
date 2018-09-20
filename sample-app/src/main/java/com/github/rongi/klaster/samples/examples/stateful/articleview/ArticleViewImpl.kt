package com.github.rongi.klaster.samples.examples.stateful.articleview

import com.github.rongi.klaster.ViewHolder
import kotlinx.android.synthetic.main.list_item.*

class ArticleViewImpl(
  private val viewHolder: ViewHolder
) : ArticleView {

  override fun setChecked(checked: Boolean) {
    viewHolder.check_box.isChecked = checked
  }

  override fun setTitle(title: String) {
    viewHolder.item_text.text = title
  }

}