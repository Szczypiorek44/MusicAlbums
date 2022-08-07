package com.example.vamatask.utils

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class BundleDelegate<T>(protected val key: String) : ReadWriteProperty<Bundle, T> {

    class IntDelegate(key: String) : BundleDelegate<Int>(key) {

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int) {
            thisRef.putInt(key, value)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): Int {
            return thisRef.getInt(key)
        }
    }
}