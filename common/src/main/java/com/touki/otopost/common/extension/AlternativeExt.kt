package com.touki.otopost.common.extension

fun Int?.orZero(): Int {
    return this ?: 0
}