package com.tejachodavarapu.holdingsdemo.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.teja.holdingsdemo.ui.theme.DeepNavy
import com.tejachodavarapu.holdingsdemo.domain.viewmodel.HoldingsViewModel
import com.tejachodavarapu.holdingsdemo.presentation.common.navigation.Screen
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.TabOption
import com.tejachodavarapu.holdingsdemo.utils.NavigationDefaults


@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    val holdingsViewModel: HoldingsViewModel = hiltViewModel()

    val indicatorHeight = NavigationDefaults.IndicatorHeight
    val indicatorWidth = NavigationDefaults.IndicatorWidth
    val iconSize = NavigationDefaults.IconSize
    val indicatorShape = RoundedCornerShape(percent = 50)
    val iconSpacing = NavigationDefaults.IconSpacing

    NavigationBar(modifier = Modifier.navigationBarsPadding()) {
        BottomNavItem.all.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute == Screen.Holdings.route && item.route == Screen.Holdings.route) {
                        holdingsViewModel.setTab(TabOption.HOLDINGS)
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (currentRoute == item.route) {
                            Box(
                                modifier = Modifier
                                    .height(indicatorHeight)
                                    .width(indicatorWidth)
                                    .background(
                                        color = DeepNavy,
                                        shape = indicatorShape
                                    )
                            )
                        } else {
                            Spacer(modifier = Modifier.height(indicatorHeight))
                        }

                        Spacer(modifier = Modifier.height(iconSpacing))

                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.label),
                            modifier = Modifier.size(iconSize),
                            tint = if (currentRoute == item.route)
                                DeepNavy
                            else
                                LocalContentColor.current
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Normal,
                            color = if (currentRoute == item.route)
                               DeepNavy
                            else
                                LocalContentColor.current
                        )
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
