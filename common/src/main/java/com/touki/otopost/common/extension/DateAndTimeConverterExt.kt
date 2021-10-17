package com.touki.otopost.common.extension

import android.util.Log
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

fun String?.toDate(format: String): Date {
    if (this.isNullOrEmpty()) {
        return Date()
    }

    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        Log.d("EXTENSION", "toDate: use Instant")
        Date(Instant.parse(this).toEpochMilli())
    } else {
        Log.d("EXTENSION", "toDate: sdf")
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val dateString = this.replace("T", " ").replace("Z", "")
        sdf.parse(dateString) ?: Date()
    }
}