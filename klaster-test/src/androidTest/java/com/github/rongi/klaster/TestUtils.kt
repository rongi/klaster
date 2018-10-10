package com.github.rongi.klaster

import org.junit.Assert

infix fun <T> Any.assertIs(clazz: Class<T>) {
  Assert.assertEquals(clazz, this.javaClass)
}

infix fun Any.assertEquals(expected: Any) {
  Assert.assertEquals(expected, this)
}

inline fun <reified R> Any?.cast(): R = this as R
