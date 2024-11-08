package com.jkhanh.shared.crypto.presentation.coin_list

import com.jkhanh.shared.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}