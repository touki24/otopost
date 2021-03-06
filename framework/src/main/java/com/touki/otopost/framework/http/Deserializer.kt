package com.touki.otopost.framework.http

import com.google.gson.Gson

internal class Deserializer<T> (private val javaClassName: Class<T>) {
    fun deserialize(content: String): T = Gson().fromJson(content, javaClassName)
}