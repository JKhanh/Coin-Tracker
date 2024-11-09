package com.jkhanh.shared

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jkhanh.shared.crypto.presentation.coin_detail.CoinDetailScreen
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListAction
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListScreen
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListViewModel
import com.jkhanh.shared.theme.CryptoTrackerTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: CoinListViewModel = koinViewModel(), dynamicColor: Boolean = false) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    CryptoTrackerTheme(
        dynamicColor = dynamicColor
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SharedTransitionLayout(modifier = Modifier.padding(innerPadding)) {
                ListDetailPaneScaffold(
                    value = navigator.scaffoldValue,
                    directive = navigator.scaffoldDirective,
                    listPane = {
                        AnimatedPane {
                            CoinListScreen(
                                state = state,
                                onAction = { action ->
                                    viewModel.onAction(action)
                                    when (action) {
                                        is CoinListAction.OnCoinClick -> {
                                            navigator.navigateTo(
                                                pane = ListDetailPaneScaffoldRole.Detail
                                            )
                                        }
                                    }
                                },
                                modifier = modifier
                            )
                        }
                    },
                    detailPane = {
                        AnimatedPane {
                            CoinDetailScreen(
                                state = state,
                                onBack = {
                                    navigator.navigateBack()
                                },
                                modifier = modifier
                            )
                        }
                    }
                )
            }
        }
    }
}