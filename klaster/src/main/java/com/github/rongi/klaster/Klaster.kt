package com.github.rongi.klaster

object Klaster {

  fun builder(): KlasterBuilder = KlasterBuilder()

}

class KlasterException(override val message: String) : RuntimeException()