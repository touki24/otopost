package com.touki.otopost.common.mapper

interface EntityToModel<E, M> {
    fun entityToModel(entity: E) : M
}