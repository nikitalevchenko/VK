package com.example.nikitalevcenko.vk.ui

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadIcon(url: String?) {
    Picasso.get()
            .load(url)
            .into(this)
}

fun ImageView.loadImage(url: String?) {
    Picasso.get()
            .load(url)
            .into(this)
}