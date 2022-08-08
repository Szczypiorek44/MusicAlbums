package com.example.vamatask.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView

fun Context.openUrl(url: String) {
    startActivity(
        Intent(Intent.ACTION_VIEW)
            .apply { data = Uri.parse(url) })
}

fun ImageView.load(url: String?) {
    GlideApp.with(this).load(url).into(this)
}