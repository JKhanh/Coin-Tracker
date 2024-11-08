package com.jkhanh.shared.crypto.presentation.coin_list

import com.jkhanh.shared.crypto.presentation.models.CoinUi

interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
}