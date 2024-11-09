package com.jkhanh.shared.crypto.presentation.coin_list.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jkhanh.shared.core.presentation.UIConstant
import com.jkhanh.shared.crypto.presentation.models.CoinUi
import org.jetbrains.compose.resources.vectorResource

@Composable
fun TopRankItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    Card(
        modifier = modifier.widthIn(min = 105.dp, max = 210.dp)
            .padding(start = 12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = vectorResource(resource = coinUi.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(UIConstant.iconSizeLarge)
            )
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = coinUi.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )
            PriceChange(
                priceChange = coinUi.changePercent24Hr,
            )
        }
    }
}