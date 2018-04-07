package com.github.rongi.rxpresenter.example

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.rules.TestRule
import org.junit.runners.model.Statement

val testSchedulersTestRule = TestRule { base, _ ->
  object : Statement() {
    override fun evaluate() {
      RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
      RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

      try {
        base.evaluate()
      } finally {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
      }
    }

  }
}

infix fun Any.assertEquals(another: Any) {
  Assert.assertEquals(this, another)
}