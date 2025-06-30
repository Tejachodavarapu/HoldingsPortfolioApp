package com.tejachodavarapu.holdingsdemo.presentation.holdings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import com.teja.holdingsdemo.R
import com.tejachodavarapu.holdingsdemo.presentation.holdings.model.HoldingUiModel

@Composable
fun HoldingCard(
    holding: HoldingUiModel,
    modifier: Modifier = Modifier
) {
    val verticalPadding = dimensionResource(id = R.dimen.card_vertical_spacing)
    val horizontalPadding = dimensionResource(id = R.dimen.card_horizontal_spacing)
    val labelValueSpacing = dimensionResource(id = R.dimen.label_value_spacing)
    val elementSpacing = dimensionResource(id = R.dimen.element_spacing)

    val ltp = holding.ltp.toFloatOrNull() ?: 0f
    val avgPrice = holding.avgPrice.toFloatOrNull() ?: 0f
    val quantity = holding.quantity.toFloatOrNull() ?: 0f

    val pnl = (ltp - avgPrice) * quantity
    val isPnlNegative = pnl < 0
    val pnlText = if (isPnlNegative) "-₹${"%.2f".format(-pnl)}" else "₹${"%.2f".format(pnl)}"
    val pnlColor = if (isPnlNegative) Color.Red else Color(0xFF2CB057)

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            )
    ) {
        val (symbol, ltpLabel, ltpValue, qtyLabel, qtyValue, pnlLabel, pnlValue) = createRefs()

        Text(
            text = holding.symbol,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.constrainAs(symbol) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )

        Text(
            text = "LTP:",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.constrainAs(ltpLabel) {
                end.linkTo(ltpValue.start, margin = labelValueSpacing)
                top.linkTo(symbol.top)
                bottom.linkTo(symbol.bottom)
            }
        )

        Text(
            text = "₹ ${holding.ltp}",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            modifier = Modifier.constrainAs(ltpValue) {
                end.linkTo(parent.end)
                top.linkTo(symbol.top)
                bottom.linkTo(symbol.bottom)
            }
        )

        Text(
            text = "NET QTY:",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.constrainAs(qtyLabel) {
                start.linkTo(parent.start)
                top.linkTo(symbol.bottom, margin = elementSpacing)
            }
        )

        Text(
            text = holding.quantity,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            modifier = Modifier.constrainAs(qtyValue) {
                start.linkTo(qtyLabel.end, margin = labelValueSpacing)
                baseline.linkTo(qtyLabel.baseline)
            }
        )

        Text(
            text = "P&L:",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.constrainAs(pnlLabel) {
                end.linkTo(pnlValue.start, margin = labelValueSpacing)
                top.linkTo(qtyLabel.top)
                bottom.linkTo(qtyLabel.bottom)
            }
        )

        Text(
            text = pnlText,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            color = pnlColor,
            modifier = Modifier.constrainAs(pnlValue) {
                end.linkTo(parent.end)
                baseline.linkTo(pnlLabel.baseline)
            }
        )
    }
}
