package com.jkhanh.shared.crypto.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jkhanh.shared.crypto.presentation.coin_detail.CoinDetailScreen
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListAction
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListScreen
import com.jkhanh.shared.crypto.presentation.coin_list.CoinListViewModel
import com.jkhanh.shared.theme.CryptoTrackerTheme
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: CoinListViewModel = koinViewModel(), dynamicColor: Boolean = false) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    CryptoTrackerTheme(
        dynamicColor = dynamicColor
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController,
                startDestination = CoinList,
                modifier = modifier.padding(innerPadding)
            ) {
                composable<CoinList> {
                    CoinListScreen(
                        state = state,
                        onAction = { action ->
                            viewModel.onAction(action)
                            when (action) {
                                is CoinListAction.OnCoinClick -> {
                                    navController.navigate(CoinDetail)
                                }
                            }
                        },
                        modifier = modifier
                    )
                }
                composable<CoinDetail> {
                    CoinDetailScreen(
                        state = state,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Serializable
object CoinList

@Serializable
object CoinDetail