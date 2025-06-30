package com.tejachodavarapu.holdingsdemo.data.repository

import com.tejachodavarapu.holdingsdemo.data.local.HoldingsDao
import com.tejachodavarapu.holdingsdemo.data.local.HoldingEntity
import com.tejachodavarapu.holdingsdemo.data.remote.api.HoldingsApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HoldingsRepository @Inject constructor(
    private val api: HoldingsApi,
    private val dao: HoldingsDao
) {

    fun getCachedHoldings(): Flow<List<HoldingEntity>> {
        return dao.getHoldings()
    }

    suspend fun syncHoldingsFromApi() {
        val apiResponse = api.getUserHoldings().data.userHolding
        val entities = apiResponse.map {
            HoldingEntity(
                symbol = it.symbol,
                quantity = it.quantity,
                ltp = it.ltp.toFloat(),
                avgPrice = it.avgPrice.toFloat(),
                close = it.close.toFloat()
            )
        }
        dao.clearHoldings()
        dao.insertHoldings(entities)
    }
}
