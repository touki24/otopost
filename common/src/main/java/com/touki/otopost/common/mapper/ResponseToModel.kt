package com.touki.otopost.common.mapper

interface ResponseToModel<R: Any?, M: Any> {
    fun responseToModel(response: R): M
}