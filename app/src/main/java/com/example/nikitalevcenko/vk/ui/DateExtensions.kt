package com.example.nikitalevcenko.vk.ui

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nikitalevcenko on 27.03.2018.
 */

val simpleDateFormat = SimpleDateFormat("dd MMMM hh:mm")

fun Long.toDateString() = simpleDateFormat.format(Date(this * 1000))!!
