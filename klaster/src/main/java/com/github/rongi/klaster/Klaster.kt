package com.github.rongi.klaster

import java.lang.RuntimeException

object Klaster {

  fun <ITEM> of(): KlasterBuilder<ITEM> = KlasterBuilder()

}

class KlasterException(override val message: String) : RuntimeException()