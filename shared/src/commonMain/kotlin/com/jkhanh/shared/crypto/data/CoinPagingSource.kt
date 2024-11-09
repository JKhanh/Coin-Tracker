package com.jkhanh.shared.crypto.data

import com.jkhanh.shared.core.data.paging.BasePagingSource
import com.jkhanh.shared.core.data.paging.PaginationException
import com.jkhanh.shared.core.domain.util.Result
import com.jkhanh.shared.crypto.data.networking.RemoteCoinDataSource
import com.jkhanh.shared.crypto.domain.Coin

class CoinPagingSource(
    private val coinService: RemoteCoinDataSource
): BasePagingSource<Coin>() {
    private val limitCoin = 20

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val page = params.key ?: 0
        return try {
            when(val result = coinService.getCoinsPaging(page * limitCoin, limitCoin)) {
                is Result.Success -> {
                    val data = result.data
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                }
                is Result.Error -> {
                    LoadResult.Error(PaginationException(result.error.name))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}