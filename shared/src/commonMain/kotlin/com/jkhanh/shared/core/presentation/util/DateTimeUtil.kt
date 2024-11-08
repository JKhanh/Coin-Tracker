package com.jkhanh.shared.core.presentation.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.Companion.now(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.plus(value: Long, unit: DateTimeUnit): LocalDateTime {
    val timezone = TimeZone.currentSystemDefault()
    return this.toInstant(timezone)
        .plus(value, unit, timezone)
        .toLocalDateTime(timezone)
}

fun LocalDateTime.minus(value: Long, unit: DateTimeUnit): LocalDateTime {
    val timezone = TimeZone.currentSystemDefault()
    return this.toInstant(timezone)
        .minus(value, unit, timezone)
        .toLocalDateTime(timezone)
}