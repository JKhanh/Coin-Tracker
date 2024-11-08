package com.jkhanh.shared.core.presentation.util

import com.jkhanh.shared.crypto.presentation.models.DisplayableNumber
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun toDisplayableNumber(number: Double): DisplayableNumber {
    val numberFormatter = NSNumberFormatter().apply {
        minimumFractionDigits = 2u
        maximumFractionDigits = 2u
        numberStyle = NSNumberFormatterDecimalStyle
    }

    return DisplayableNumber(
        value = number,
        formatted = numberFormatter.stringFromNumber(NSNumber(number)) ?: ""
    )
}