package com.tejachodavarapu.holdingsdemo.presentation.holdings.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.teja.holdingsdemo.R
import com.teja.holdingsdemo.ui.theme.DeepNavy
import com.tejachodavarapu.holdingsdemo.presentation.holdings.components.HoldingCard
import com.tejachodavarapu.holdingsdemo.presentation.holdings.components.HoldingsTabSelector
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.TabOption
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tejachodavarapu.holdingsdemo.domain.viewmodel.HoldingsViewModel


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun HoldingsScreen( viewModel: HoldingsViewModel = hiltViewModel()) {
    val windowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
    val holdings by viewModel.holdings.collectAsState()
    val selectedTab by viewModel.selectedTab
    val totalPnL by viewModel.totalPnL
    val currentValue by viewModel.currentValue
    val totalInvestment by viewModel.totalInvestment
    val todaysPnL by viewModel.todaysPnL
    val percentageChange by viewModel.percentageChange
    var isPnLExpanded by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        val (topBar, tabSelector, contentList, pnlSummary) = createRefs()

        PortfolioTopBar(windowSizeClass.widthSizeClass,
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        HoldingsTabSelector(
            selectedTab = selectedTab,
            onTabSelected = { viewModel.setTab(it) },
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.tab_horizontal_padding),
                    vertical = dimensionResource(R.dimen.tab_vertical_padding)
                )
                .constrainAs(tabSelector) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        if (selectedTab == TabOption.HOLDINGS) {
            LazyColumn(
                modifier = Modifier
                    .constrainAs(contentList) {
                        top.linkTo(tabSelector.bottom)
                        bottom.linkTo(pnlSummary.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    }
            ) {
                item {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                        thickness = dimensionResource(id = R.dimen.divider_thickness),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
                items(items = holdings, key = { it.symbol }) { holding ->
                    Column {
                        HoldingCard(holding = holding)
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding)),
                            thickness = dimensionResource(id = R.dimen.divider_thickness),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )
                    }
                }
            }


                ProfitAndLossSummary(
                totalPnL = totalPnL,
                percentageChange = percentageChange,
                currentValue = currentValue,
                totalInvestment = totalInvestment,
                todaysPnL = todaysPnL,
                isExpanded = isPnLExpanded,
                onToggle = { isPnLExpanded = !isPnLExpanded },
                modifier = Modifier.constrainAs(pnlSummary) {
                    bottom.linkTo(parent.bottom, margin = 70.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}


@Composable
fun PortfolioTopBar(windowSizeClass: WindowWidthSizeClass, modifier: Modifier) {
    val iconSize = when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> dimensionResource(R.dimen.icon_size_small)
        WindowWidthSizeClass.Medium -> dimensionResource(R.dimen.icon_size_medium)
        WindowWidthSizeClass.Expanded -> dimensionResource(R.dimen.icon_size_large)
        else -> dimensionResource(R.dimen.icon_size_small)
    }

    val topBarHeight = dimensionResource(R.dimen.top_bar_height)
    val horizontalPadding = dimensionResource(R.dimen.horizontal_padding)
    val iconTextSpacing = dimensionResource(R.dimen.icon_text_spacing)
    val iconSpacing = dimensionResource(R.dimen.icon_spacing)
    val dividerWidth = dimensionResource(R.dimen.divider_width)
    val dividerHeight = dimensionResource(R.dimen.divider_height)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(DeepNavy)
            .padding(horizontal = horizontalPadding)
    ) {
        val (profileIcon, title, sortIcon, divider, searchIcon) = createRefs()

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = stringResource(id = R.string.portfolio),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(iconSize)
                .constrainAs(profileIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = stringResource(id = R.string.portfolio),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(profileIcon.end, margin = iconTextSpacing)
                end.linkTo(sortIcon.start, margin = iconTextSpacing)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(id = R.drawable.swapvert),
            contentDescription = stringResource(id = R.string.sort),
            modifier = Modifier
                .size(iconSize)
                .constrainAs(sortIcon) {
                    end.linkTo(divider.start, margin = iconSpacing)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Box(
            modifier = Modifier
                .width(dividerWidth)
                .height(dividerHeight)
                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f))
                .constrainAs(divider) {
                    end.linkTo(searchIcon.start, margin = iconSpacing)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(iconSize)
                .constrainAs(searchIcon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HoldingsScreenPreview() {
    HoldingsScreen()
}
