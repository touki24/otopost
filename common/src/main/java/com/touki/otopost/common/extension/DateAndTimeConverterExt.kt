package com.touki.otopost.common.extension

import android.util.Log
import com.touki.otopost.common.constant.DATE_TIME_ISO_8601
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

fun Date.toString(format: String): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}

fun Date.toIso8601(): String {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val dtf = DateTimeFormatter.ofPattern(DATE_TIME_ISO_8601)
        val zdt = this.toInstant().atZone(ZoneOffset.UTC)
        return dtf.format(zdt)
    } else {
        //TODO: not tested yep, hope this won't crash on android bellow oreo
        return this.toString(DATE_TIME_ISO_8601)
    }
}