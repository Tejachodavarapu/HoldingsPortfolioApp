package com.tejachodavarapu.holdingsdemo.presentation.holdings.view

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.teja.holdingsdemo.R
import com.teja.holdingsdemo.ui.theme.GrayBackground

@Composable
fun ProfitAndLossSummary(
    totalPnL: String,
    percentageChange: String,
    currentValue: String,
    totalInvestment: String,
    todaysPnL: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier
) {
    val rawPnl = totalPnL.replace("₹", "").trim()
    val pnlValue = rawPnl.toDoubleOrNull() ?: 0.0
    val pnlColor = if (pnlValue < 0) Color.Red else Color(0xFF00C853)

    val pnlText = if (pnlValue < 0) "-₹%.2f".format(-pnlValue) else "₹%.2f".format(pnlValue)

    val cleanPercentage = percentageChange.trim().removeSuffix("%")
    val percentageText = if (cleanPercentage.startsWith("-")) {
        "(${cleanPercentage}%)"
    } else {
        "(+${cleanPercentage}%)"
    }

    val horizontalPadding = dimensionResource(id = R.dimen.horizontal_padding)
    val verticalPadding = dimensionResource(id = R.dimen.vertical_padding)
    val cornerRadius = dimensionResource(id = R.dimen.corner_radius)
    val iconSize = dimensionResource(id = R.dimen.icon_size_small)
    val dividerThickness = dimensionResource(id = R.dimen.divider_thickness)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        color =  GrayBackground
    ) {
        Column(modifier = Modifier.padding(all = verticalPadding)) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(animationSpec = tween(350, easing = FastOutSlowInEasing)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(350, easing = FastOutSlowInEasing))
            ) {
                Column {
                    InfoRow(stringResource(id = R.string.current_value), currentValue)
                    InfoRow(stringResource(id = R.string.total_investment), totalInvestment)
                    InfoRow(stringResource(id = R.string.todays_pnl), todaysPnL, isProfit = !todaysPnL.startsWith("-"))

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
                        thickness = dividerThickness,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            ) {
                val (label, arrow, value) = createRefs()

                Text(
                    text = stringResource(id = R.string.profit_and_loss),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.constrainAs(label) {
                        start.linkTo(parent.start)
                        centerVerticallyTo(parent)
                    }
                )

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(iconSize)
                        .constrainAs(arrow) {
                            start.linkTo(label.end, margin = 4.dp)
                            centerVerticallyTo(parent)
                        }
                )

                Text(
                    text = "$pnlText $percentageText",
                    color = pnlColor,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.constrainAs(value) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    }
                )

            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    isProfit: Boolean = true
) {
    val horizontalPadding = dimensionResource(id = R.dimen.horizontal_padding)
    val verticalPadding = dimensionResource(id = R.dimen.vertical_padding_small)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
    ) {
        val (labelRef, valueRef) = createRefs()

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(labelRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = if (value.startsWith("-")) Color.Red else if (isProfit) Color(0xFF00C853) else LocalContentColor.current,
            modifier = Modifier.constrainAs(valueRef) {
                end.linkTo(parent.end)
                top.linkTo(labelRef.top)
            }
        )
    }
}
