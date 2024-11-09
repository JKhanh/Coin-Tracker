package com.jkhanh.shared.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import app.cash.paging.Pager
import app.cash.paging.map
import com.jkhanh.shared.core.domain.util.onError
import com.jkhanh.shared.core.domain.util.onSuccess
import com.jkhanh.shared.core.presentation.util.minus
import com.jkhanh.shared.core.presentation.util.now
import com.jkhanh.shared.crypto.domain.Coin
import com.jkhanh.shared.crypto.domain.CoinDataSource
import com.jkhanh.shared.crypto.presentation.coin_detail.DataPoint
import com.jkhanh.shared.crypto.presentation.models.CoinUi
import com.jkhanh.shared.crypto.presentation.models.toCoinUi
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

class CoinListViewModel(
    pager: Pager<Int, Coin>,
    private val coinDataSource: CoinDataSource
): ViewModel() {

    var coinListPagerFlow = pager.flow
        .map { pagingData ->
            pagingData.map { item ->
                item.toCoinUi()
            }
        }
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }
        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinUi.id,
                start = LocalDateTime.now().minus(5, DateTimeUnit.DAY),
                end = LocalDateTime.now()
            )
            .onSuccess { history ->
                val dataPoints = history
                    .sortedBy { it.datetime }
                    .map {
                        DataPoint(
                            x = it.datetime.hour.toFloat(),
                            y = it.priceUsd.toFloat(),
                            xLabel = it.datetime.format(
                                LocalDateTime.Format {
                                    amPmHour()
                                    chars("\n")
                                    dayOfMonth()
                                    char('/')
                                    monthNumber()
                                }
                            )
                        )
                    }
                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = dataPoints
                        )
                    )
                }
            }
            .onError { error ->
                _events.send(CoinListEvent.Error(error))
            }
        }
    }
}