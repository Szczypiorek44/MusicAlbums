package com.example.vamatask.utils

import android.os.Bundle
import com.example.data.models.Album
import java.io.Serializable
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

    class AlbumList(key: String) : BundleDelegate<List<Album>>(key) {

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: List<Album>) {
            thisRef.putSerializable(key, value as Serializable)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): List<Album> {
            return (thisRef.getSerializable(key) as List<Album>)
        }
    }
}