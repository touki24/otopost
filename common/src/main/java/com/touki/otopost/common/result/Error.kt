package com.touki.otopost.common.result

sealed class Error(open val message: String)

data class DatabaseError(override val message: String): Error(message)