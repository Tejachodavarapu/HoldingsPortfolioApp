package com.tejachodavarapu.holdingsdemo.presentation.holdings.model


data class HoldingUiModel(
    val symbol: String,
    val ltp: String,
    val quantity: String,
    val avgPrice: String,
    val close: String
)
