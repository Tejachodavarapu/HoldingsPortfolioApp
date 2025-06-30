package com.tejachodavarapu.holdingsdemo.presentation.common.navigation


sealed class Screen(val route: String) {

    object Holdings : Screen("holdings_screen")

    object HoldingDetail : Screen("holding_detail/{symbol}") {
        fun createRoute(symbol: String) = "holding_detail/$symbol"
    }


    object Watchlist : Screen("watchlist")

    object Orders : Screen("orders")

    object Funds : Screen("funds")

    object Invest : Screen("invest")
}
