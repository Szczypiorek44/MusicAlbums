package com.example.vamatask.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun Context.openUrl(url: String) {
    startActivity(
        Intent(Intent.ACTION_VIEW)
            .apply { data = Uri.parse(url) })
}

fun ImageView.load(url: String?) {
    Glide.with(this).load(url).into(this)
}