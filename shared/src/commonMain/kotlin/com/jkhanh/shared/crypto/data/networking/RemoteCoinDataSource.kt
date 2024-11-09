package com.jkhanh.shared.crypto.data.networking

import androidx.compose.ui.text.Paragraph
import com.jkhanh.shared.core.data.networking.constructUrl
import com.jkhanh.shared.core.data.networking.safeCall
import com.jkhanh.shared.core.domain.util.NetworkError
import com.jkhanh.shared.core.domain.util.Result
import com.jkhanh.shared.core.domain.util.map
import com.jkhanh.shared.crypto.data.mappers.toCoin
import com.jkhanh.shared.crypto.data.mappers.toCoinPrice
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.jkhanh.shared.crypto.domain.Coin
import com.jkhanh.shared.crypto.domain.CoinDataSource
import com.jkhanh.shared.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinsPaging(offset: Int, limit: Int): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            ) {
                parameter("offset", offset)
                parameter("limit", limit)
            }
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: LocalDateTime,
        end: LocalDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startTime = start
            .toInstant(TimeZone.UTC)
            .toEpochMilliseconds()
        val endTime = end
            .toInstant(TimeZone.UTC)
            .toEpochMilliseconds()
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startTime)
                parameter("end", endTime)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}