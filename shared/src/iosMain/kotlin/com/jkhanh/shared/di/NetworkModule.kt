package com.jkhanh.shared.di

import com.jkhanh.shared.core.data.networking.HttpClientFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val networkModule = module {
    single { HttpClientFactory.create(Darwin.create())}
}