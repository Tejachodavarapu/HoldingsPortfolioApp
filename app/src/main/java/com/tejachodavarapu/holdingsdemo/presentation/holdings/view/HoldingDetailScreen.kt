package com.tejachodavarapu.holdingsdemo.presentation.holdings.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HoldingDetailScreen(symbol: String) {
    Text(text = "Details for $symbol")
}
