package com.jkhanh.shared.crypto.presentation.coin_list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.compose.collectAsLazyPagingItems
import com.jkhanh.shared.core.presentation.ErrorMessageView
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.jkhanh.shared.crypto.presentation.coin_list.components.CoinListItem
import com.jkhanh.shared.crypto.presentation.coin_list.components.TopRankItem
import org.koin.compose.viewmodel.koinViewModel

const val maxTopBoxSize = 210f
const val minTopBoxSize = 0f

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {

    val coins = viewModel.coinListPagerFlow.collectAsLazyPagingItems()

    var isRefreshing by remember { mutableStateOf(false) }

    var topRankingBoxHeight by remember {
        mutableStateOf(maxTopBoxSize)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val newImageSize = topRankingBoxHeight + delta
                val previousSize = topRankingBoxHeight
                topRankingBoxHeight = newImageSize.coerceIn(minTopBoxSize, maxTopBoxSize)
                val consumed = topRankingBoxHeight - previousSize

                return Offset(0f, consumed)
            }

        }
    }

    Column(modifier = modifier
        .nestedScroll(nestedScrollConnection)
        .fillMaxSize()
        .padding(top = 8.dp)
    ) {
        if (state.isLoading) {
            // Loading
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val topRanks = coins.itemSnapshotList.items
                .sortedByDescending { it.rank }
                .take(3)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(topRanks) { coinUi ->
                    TopRankItem(
                        coinUi = coinUi,
                        onClick = {
                            onAction(CoinListAction.OnCoinClick(coinUi))
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(coins.itemCount) { index ->
                    coins[index]?.let { coinUi ->
                        if (topRanks.any { it.id != coinUi.id }) {
                            CoinListItem(
                                coinUi = coinUi,
                                onClick = { onAction(CoinListAction.OnCoinClick(coinUi)) }
                            )
                            HorizontalDivider()
                        }
                    }
                }

                item {
                    when {
                        coins.loadState.refresh is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        coins.loadState.append is LoadState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        coins.loadState.refresh is LoadState.Error -> {
                            val e = coins.loadState.refresh as LoadState.Error
                            ErrorMessageView(message = e.error.message) {
                                coins.retry()
                            }
                        }
                        coins.loadState.append is LoadState.Error -> {
                            val e = coins.loadState.append as LoadState.Error
                            ErrorMessageView(message = e.error.message) {
                                coins.retry()
                            }
                        }
                    }
                }
            }
        }
    }
}