package com.hmduc.foody.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observerOnce(livedataOwner: LifecycleOwner,observer: Observer<T>) {
    observe(livedataOwner, object: Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}