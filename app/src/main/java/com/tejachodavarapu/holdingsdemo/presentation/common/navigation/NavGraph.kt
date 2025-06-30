package com.tejachodavarapu.holdingsdemo.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tejachodavarapu.holdingsdemo.presentation.funds.FundsScreen
import com.tejachodavarapu.holdingsdemo.presentation.holdings.view.HoldingDetailScreen
import com.tejachodavarapu.holdingsdemo.presentation.holdings.view.HoldingsScreen
import com.tejachodavarapu.holdingsdemo.presentation.invest.InvestScreen
import com.tejachodavarapu.holdingsdemo.presentation.orders.OrdersScreen
import com.tejachodavarapu.holdingsdemo.presentation.watchlist.WatchlistScreen


@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Holdings.route
    ) {
        composable(Screen.Holdings.route) {
            HoldingsScreen()
        }

        composable(
            route = Screen.HoldingDetail.route,
            arguments = listOf(navArgument("symbol") { type = NavType.StringType })
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            HoldingDetailScreen(symbol)
        }
        composable(Screen.Watchlist.route) {
            WatchlistScreen()
        }
        composable(Screen.Orders.route) {
            OrdersScreen()
        }
        composable(Screen.Funds.route) {
            FundsScreen()
        }
        composable(Screen.Invest.route) {
            InvestScreen()
        }

    }
}