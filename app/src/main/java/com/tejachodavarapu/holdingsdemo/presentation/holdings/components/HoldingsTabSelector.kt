package com.tejachodavarapu.holdingsdemo.presentation.holdings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.teja.holdingsdemo.R
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.TabOption

@Composable
fun HoldingsTabSelector(
    selectedTab: TabOption,
    onTabSelected: (TabOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val horizontalPadding = dimensionResource(id = R.dimen.tab_horizontal_padding)
    val indicatorWidth = dimensionResource(id = R.dimen.tab_indicator_width)
    val indicatorHeight = dimensionResource(id = R.dimen.tab_indicator_height)
    val indicatorTopMargin = dimensionResource(id = R.dimen.tab_indicator_top_margin)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        val (positionsTab, holdingsTab, indicator) = createRefs()

        Text(
            text = stringResource(id = R.string.positions),
            style = MaterialTheme.typography.labelLarge,
            color = if (selectedTab == TabOption.POSITIONS) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
            fontWeight = if (selectedTab == TabOption.POSITIONS) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .constrainAs(positionsTab) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { onTabSelected(TabOption.POSITIONS) }
        )

        Text(
            text = stringResource(id = R.string.holdings),
            style = MaterialTheme.typography.labelLarge,
            color = if (selectedTab == TabOption.HOLDINGS) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline,
            fontWeight = if (selectedTab == TabOption.HOLDINGS) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .constrainAs(holdingsTab) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { onTabSelected(TabOption.HOLDINGS) }
        )

        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(indicatorHeight)
                .background(MaterialTheme.colorScheme.onSurface)
                .constrainAs(indicator) {
                    top.linkTo(holdingsTab.bottom, margin = indicatorTopMargin)
                    start.linkTo(
                        if (selectedTab == TabOption.POSITIONS) positionsTab.start else holdingsTab.start
                    )
                    end.linkTo(
                        if (selectedTab == TabOption.POSITIONS) positionsTab.end else holdingsTab.end
                    )
                }
        )
    }
}
