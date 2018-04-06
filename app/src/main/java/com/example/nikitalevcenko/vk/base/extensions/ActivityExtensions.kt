package com.example.nikitalevcenko.vk.base.extensions

import android.app.Activity
import android.support.annotation.StringRes
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import com.example.nikitalevcenko.vk.R


fun Activity.showMessage(@StringRes message: Int) {
    this.showMessage(getString(message))
}

fun Activity.showMessage(message: String) {
    Snackbar.make(this.findViewById<CoordinatorLayout>(R.id.coordinatorLayout),
            message,
            Snackbar.LENGTH_SHORT)
            .show()
}
