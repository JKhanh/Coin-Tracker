package com.jkhanh.shared.crypto.domain

import kotlinx.datetime.LocalDateTime

data class CoinPrice(
    val priceUsd: Double,
    val datetime: LocalDateTime
)
