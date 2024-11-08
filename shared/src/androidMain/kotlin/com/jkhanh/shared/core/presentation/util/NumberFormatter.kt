package com.jkhanh.shared.core.presentation.util

import com.jkhanh.shared.crypto.presentation.coin_detail.ValueLabel
import java.text.NumberFormat
import java.util.Locale

actual fun formatNumber(valueLabel: ValueLabel): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        val fractionDigits = when {
            valueLabel.value > 1000 -> 0
            valueLabel.value in 2f..999f -> 2
            else -> 3
        }
        maximumFractionDigits = fractionDigits
        minimumFractionDigits = 0
    }
    return "${formatter.format(valueLabel.value)}${valueLabel.unit}"
}