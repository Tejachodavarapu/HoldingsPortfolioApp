package com.tejachodavarapu.holdingsdemo.presentation.common


import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.teja.holdingsdemo.R

sealed class BottomNavItem(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    object Watchlist : BottomNavItem("watchlist", R.drawable.watchlist_icon, R.string.watchlist)
    object Orders : BottomNavItem("orders", R.drawable.orders_icon, R.string.orders)

    object Portfolio : BottomNavItem("holdings_screen", R.drawable.portfolio_icon, R.string.portfolio)

    object Funds : BottomNavItem("funds", R.drawable.funds_icon, R.string.funds)
    object Invest : BottomNavItem("invest", R.drawable.invest_icon, R.string.invest)

    companion object {
        val all = listOf(Watchlist, Orders, Portfolio, Funds, Invest)
    }
}
