package com.jkhanh.shared.crypto.domain

import com.jkhanh.shared.core.domain.util.NetworkError
import com.jkhanh.shared.core.domain.util.Result
import kotlinx.datetime.LocalDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinsPaging(offset: Int, limit: Int): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): Result<List<CoinPrice>, NetworkError>
}