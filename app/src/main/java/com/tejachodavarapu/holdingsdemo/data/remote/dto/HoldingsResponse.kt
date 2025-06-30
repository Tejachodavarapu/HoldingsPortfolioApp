package com.tejachodavarapu.holdingsdemo.data.remote.dto


data class HoldingsResponse(
    val data: HoldingsData
)

data class HoldingsData(
    val userHolding: List<UserHoldingDto>
)

data class UserHoldingDto(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)