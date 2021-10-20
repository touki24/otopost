package com.touki.otopost.common.mapper

interface ModelToModel<M1, M2> {
    fun toSpecificModel(model: M1): M2
}