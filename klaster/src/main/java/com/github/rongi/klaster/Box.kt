package com.github.rongi.klaster

import java.lang.RuntimeException

object Box {

  fun <ITEM> of(): BoxBuilder<ITEM> = BoxBuilder()

}

class BoxException(override val message: String) : RuntimeException()