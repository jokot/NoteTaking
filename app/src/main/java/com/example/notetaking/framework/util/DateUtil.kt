package com.example.notetaking.framework.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDate(format: String = "MMM dd, HH:mm:ss"): String {
    val sdf = SimpleDateFormat(format, Locale.US)
    val date = Date(this)
    return sdf.format(date)
}