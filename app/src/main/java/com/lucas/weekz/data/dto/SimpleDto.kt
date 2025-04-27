package com.lucas.weekz.data.dto

import com.lucas.weekz.domain.model.SimpleModel


data class SimpleDto(
    val id: Int,
) {
    fun toModel() = SimpleModel(
        id = this.id
    )
}
