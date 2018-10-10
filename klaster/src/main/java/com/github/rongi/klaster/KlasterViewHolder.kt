package com.github.rongi.klaster

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

/**
 * Simple view holder with empty implementation.
 *
 * It's designed to be used with Kotlin Android Extensions.
 */
class KlasterViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
