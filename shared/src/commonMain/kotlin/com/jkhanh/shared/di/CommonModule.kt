package com.jkhanh.shared.di

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import com.jkhanh.shared.core.data.networking.HttpClientFactory
import com.jkhanh.shared.crypto.data.CoinPagingSource
import com.jkhanh.shared.crypto.data.networking.RemoteCoinDataSource
import com.jkhanh.shared.crypto.domain.CoinDataSource
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    factory { CoinPagingSource(get()) }

    single {
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2, initialLoadSize = 2),
            pagingSourceFactory = { get<CoinPagingSource>() }
        )
    }

    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}