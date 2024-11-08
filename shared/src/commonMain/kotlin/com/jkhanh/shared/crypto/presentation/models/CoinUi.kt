package com.jkhanh.shared.crypto.presentation.models

import com.jkhanh.shared.core.presentation.util.getDrawableIdForCoin
import com.jkhanh.shared.core.presentation.util.toDisplayableNumber
import com.jkhanh.shared.crypto.domain.Coin
import com.jkhanh.shared.crypto.presentation.coin_detail.DataPoint
import org.jetbrains.compose.resources.DrawableResource

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    val icon: DrawableResource
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        marketCapUsd = toDisplayableNumber(marketCapUsd),
        priceUsd = toDisplayableNumber(priceUsd),
        changePercent24Hr = toDisplayableNumber(changePercent24Hr),
        icon = getDrawableIdForCoin(symbol = symbol)
    )
}