package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.presentation.models.DisplayableNumber
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.greenBackground

@Composable
fun PriceChange(
    priceChange: DisplayableNumber,
    modifier: Modifier = Modifier
) {
    val contentColor = if(priceChange.value >= 0) {
        Color.Green
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
    val backgroundColor = if(priceChange.value >= 0) {
        greenBackground
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(backgroundColor)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = if(priceChange.value >= 0) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowUp
            },
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = "${priceChange.formatted} %",
            color = contentColor,
            fontSize = 14.sp
        )
    }
}

@PreviewLightDark
@Composable
private fun PriceChangePreview() {
    CryptoTrackerTheme {
        PriceChange(
            priceChange = DisplayableNumber(
                2.34,
                "2.34")
        )
    }
}