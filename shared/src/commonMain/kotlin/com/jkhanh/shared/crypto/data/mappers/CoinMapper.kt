package com.jkhanh.shared.crypto.data.mappers

import com.jkhanh.shared.crypto.data.networking.dto.CoinDto
import com.jkhanh.shared.crypto.domain.CoinPrice
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.jkhanh.shared.crypto.domain.Coin
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun CoinDto.toCoin() = Coin(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr
)

fun CoinPriceDto.toCoinPrice() = CoinPrice(
    priceUsd = priceUsd,
    datetime = Instant.fromEpochMilliseconds(time)
        .toLocalDateTime(TimeZone.currentSystemDefault())
)