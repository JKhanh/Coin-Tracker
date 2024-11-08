package com.jkhanh.shared.crypto.presentation.coin_detail

import com.jkhanh.shared.core.presentation.util.formatNumber

data class ValueLabel(
    val value: Float,
    val unit: String
) {
    fun formattedValue(): String {
        return formatNumber(this)
    }
}
