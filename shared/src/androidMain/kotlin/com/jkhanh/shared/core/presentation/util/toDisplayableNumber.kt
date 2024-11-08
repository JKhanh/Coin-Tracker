package com.jkhanh.shared.core.presentation.util

import com.jkhanh.shared.crypto.presentation.models.DisplayableNumber
import java.text.NumberFormat
import java.util.Locale

actual fun toDisplayableNumber(number: Double): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = number,
        formatted = formatter.format(number)
    )
}