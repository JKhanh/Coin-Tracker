package com.jkhanh.shared.di

import com.jkhanh.shared.core.data.networking.HttpClientFactory
import com.jkhanh.shared.crypto.data.networking.RemoteCoinDataSource
import com.jkhanh.shared.crypto.domain.CoinDataSource
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}