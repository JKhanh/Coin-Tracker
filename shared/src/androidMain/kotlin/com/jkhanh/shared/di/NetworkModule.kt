package com.jkhanh.shared.di

import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module
import com.jkhanh.shared.core.data.networking.HttpClientFactory

actual val networkModule = module {
    single { HttpClientFactory.create(CIO.create())}
}