package com.example.nikitalevcenko.vk.ui

import android.app.Activity
import android.support.annotation.StringRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.example.nikitalevcenko.vk.R
import com.example.nikitalevcenko.vk.exceptions.ServerError
import java.net.UnknownHostException


fun Activity.showMessage(@StringRes message: Int) {
    this.showMessage(getString(message))
}

fun Activity.showMessage(message: String) {
    Snackbar.make(this.findViewById<CoordinatorLayout>(R.id.coordinatorLayout),
            message,
            Snackbar.LENGTH_SHORT)
            .show()
}

fun Activity.handleError(throwable: Throwable?) {
    if (throwable == null) return

    when (throwable::class) {
        UnknownHostException::class -> return
        ServerError::class -> this.showError(R.string.server_error)
        else -> this.showError(R.string.unknown_error)
    }
}

fun Activity.showError(@StringRes error: Int) {
    val snackbar = Snackbar.make(this.findViewById<CoordinatorLayout>(R.id.coordinatorLayout),
            error,
            Snackbar.LENGTH_SHORT)

    snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.error_color_material))

    snackbar.show()
}