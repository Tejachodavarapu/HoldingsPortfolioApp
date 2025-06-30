package com.tejachodavarapu.holdingsdemo.domain.viewmodel

import android.content.Context
import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejachodavarapu.holdingsdemo.data.local.HoldingEntity
import com.tejachodavarapu.holdingsdemo.data.repository.HoldingsRepository
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.HoldingUiModel
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.TabOption
import com.tejachodavarapu.holdingsdemo.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import java.util.Locale

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: HoldingsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _selectedTab = mutableStateOf(TabOption.HOLDINGS)
    val selectedTab: State<TabOption> = _selectedTab

    private val _holdings = MutableStateFlow<List<HoldingUiModel>>(emptyList())
    val holdings: StateFlow<List<HoldingUiModel>> = _holdings.asStateFlow()

    private val _totalPnL = mutableStateOf("0.00")
    val totalPnL: State<String> = _totalPnL

    private val _currentValue = mutableStateOf("0.00")
    val currentValue: State<String> = _currentValue

    private val _totalInvestment = mutableStateOf("0.00")
    val totalInvestment: State<String> = _totalInvestment

    private val _todaysPnL = mutableStateOf("0.00")
    val todaysPnL: State<String> = _todaysPnL

    private val _percentageChange = mutableStateOf("0.00%")
    val percentageChange: State<String> = _percentageChange

    init {
        if (NetworkUtils.isNetworkAvailable(context)) {
            fetchAndCache() // sync API → Room
        }
        observeRoomData() // always observe Room
    }

    fun setTab(tab: TabOption) {
        _selectedTab.value = tab
    }

    private fun fetchAndCache() {
        viewModelScope.launch {
            try {
                repository.syncHoldingsFromApi()
            } catch (e: Exception) {
                Timber.tag("HoldingsViewModel").e(e, "Error syncing holdings from API")
            }
        }
    }


    private fun observeRoomData() {
        viewModelScope.launch {
            repository.getCachedHoldings().collect { cached ->
                _holdings.value = cached.map {
                    HoldingUiModel(
                        symbol = it.symbol,
                        ltp = it.ltp.toString(),
                        quantity = it.quantity.toString(),
                        avgPrice = it.avgPrice.toString(),
                        close = it.close.toString()
                    )
                }

                calculatePnLFromEntities(cached)
            }
        }

    }

    private fun calculatePnLFromEntities(entities: List<HoldingEntity>) {
        var currentValueTotal = 0.0
        var investmentTotal = 0.0
        var todaysPnLTotal = 0.0

        for (holding in entities) {
            currentValueTotal += holding.ltp * holding.quantity
            investmentTotal += holding.avgPrice * holding.quantity
            todaysPnLTotal += (holding.ltp - holding.close) * holding.quantity
        }

        val pnl = currentValueTotal - investmentTotal
        val percent = if (investmentTotal != 0.0) (pnl / investmentTotal) * 100 else 0.0

        _currentValue.value = "₹${String.format(Locale.US, "%.2f", currentValueTotal)}"
        _totalInvestment.value = "₹${String.format(Locale.US, "%.2f", investmentTotal)}"
        _totalPnL.value = "₹${String.format(Locale.US, "%.2f", pnl)}"
        _todaysPnL.value = "₹${String.format(Locale.US, "%.2f", todaysPnLTotal)}"
        _percentageChange.value = "${String.format(Locale.US, "%.2f", percent)}%"
    }
}
