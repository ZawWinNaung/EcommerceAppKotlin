package com.example.ecommerceapp.util

import java.util.Locale

fun Double.toAmount(): String {
    return String.format(Locale.US, "%.3f", this).substringBefore(".") + "." +
            String.format(Locale.US, "%.3f", this).substringAfter(".").take(2)
}