package com.touki.otopost.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun String?.toDate(format: String): Date {
    val sdf = SimpleDateFormat(format, Locale.getDefault())

    if (this.isNullOrEmpty()) {
        return Date()
    }

    return sdf.parse(this) ?: Date()
}