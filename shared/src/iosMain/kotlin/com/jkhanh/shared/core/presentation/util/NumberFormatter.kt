package com.jkhanh.shared.core.presentation.util

import com.jkhanh.shared.crypto.presentation.coin_detail.ValueLabel
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.darwin.NSUInteger

actual fun formatNumber(valueLabel: ValueLabel): String {
    val formatter = NSNumberFormatter().apply {
        val fractionDigits: NSUInteger = when {
            valueLabel.value > 1000 -> 0u
            valueLabel.value in 2f..999f -> 2u
            else -> 3u
        }
        minimumFractionDigits = fractionDigits
        maximumFractionDigits = 2u
    }

    return "${formatter.stringFromNumber(NSNumber(valueLabel.value))}${valueLabel.unit}"
}