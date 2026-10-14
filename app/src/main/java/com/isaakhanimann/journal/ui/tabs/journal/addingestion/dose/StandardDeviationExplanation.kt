package com.isaakhanimann.journal.ui.tabs.journal.addingestion.dose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.search.substance.roa.toPreservedString
import com.isaakhanimann.journal.ui.tabs.search.substance.roa.toReadableString

@Composable
fun StandardDeviationExplanation(
    modifier: Modifier = Modifier,
    mean: Double,
    standardDeviation: Double,
    unit: String
) {
    val meanStr = mean.toPreservedString()
    val sdStr = standardDeviation.toPreservedString()
    val lower1 = (mean - standardDeviation).toReadableString()
    val higher1 = (mean + standardDeviation).toReadableString()
    val lower2 = (mean - 2 * standardDeviation).toReadableString()
    val higher2 = (mean + 2 * standardDeviation).toReadableString()

    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Text(stringResource(R.string.journal_sd_explanation_means, meanStr, sdStr, unit))
        Text(stringResource(R.string.journal_sd_68_percent, lower1, higher1, unit))
        Text(stringResource(R.string.journal_sd_95_percent, lower2, higher2, unit))
    }
}

@Preview
@Composable
private fun StandardDeviationExplanationPreview() {
    StandardDeviationExplanation(mean = 20.0, standardDeviation = 3.0, unit = "mg")
}
