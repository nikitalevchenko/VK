package com.example.nikitalevcenko.vk.ui

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

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

fun ImageView.loadImage(imageUrl: String?, smallImageUrl: String?) {
    Picasso.get()
            .load(smallImageUrl)
            .into(this, object : Callback {
                override fun onSuccess() {
                    if (!imageUrl.equals(smallImageUrl)) {
                        loadImage(imageUrl)
                    }
                }

                override fun onError(e: Exception?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
}