package com.touki.otopost.common.mapper

interface ModelToEntity<M, E> {
    fun modelToEntity(model: M): E
}