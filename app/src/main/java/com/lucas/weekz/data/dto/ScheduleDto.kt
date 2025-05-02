package com.lucas.weekz.data.dto

data class ScheduleDto(
    val id: Int = 0,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val memo: String
)