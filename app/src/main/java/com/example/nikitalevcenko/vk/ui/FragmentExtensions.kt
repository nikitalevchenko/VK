package com.example.nikitalevcenko.vk.ui

import android.support.annotation.StringRes
import android.support.v4.app.Fragment

fun Fragment.showMessage(@StringRes message: Int) {
    activity?.showMessage(message)
}

fun Fragment.showMessage(message: String) {
    activity?.showMessage(message)
}

fun Fragment.handleError(throwable: Throwable?) {
    activity?.handleError(throwable)
}