package com.jkhanh.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.jkhanh.shared.crypto.presentation.MainScreen
import com.jkhanh.shared.di.commonModule
import com.jkhanh.shared.di.networkModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(commonModule, networkModule)
        }
    }
) {
    MainScreen(dynamicColor = false)
}