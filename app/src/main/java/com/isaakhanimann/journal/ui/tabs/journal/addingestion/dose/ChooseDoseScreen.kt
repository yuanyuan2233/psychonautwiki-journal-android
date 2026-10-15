/*
 * Copyright (c) 2022-2023. Isaak Hanimann.
 * This file is part of PsychonautWiki Journal.
 *
 * PsychonautWiki Journal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * PsychonautWiki Journal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PsychonautWiki Journal.  If not, see https://www.gnu.org/licenses/gpl-3.0.en.html.
 */

package com.isaakhanimann.journal.ui.tabs.journal.addingestion.dose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.substances.AdministrationRoute
import com.isaakhanimann.journal.ui.tabs.journal.getLocalizedNameRes
import com.isaakhanimann.journal.data.substances.classes.roa.DoseClass
import com.isaakhanimann.journal.data.substances.classes.roa.RoaDose
import com.isaakhanimann.journal.ui.DOSE_DISCLAIMER
import com.isaakhanimann.journal.ui.tabs.search.substance.roa.dose.RoaDosePreviewProvider
import com.isaakhanimann.journal.ui.tabs.search.substance.roa.dose.RoaDoseView
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@Composable
fun ChooseDoseScreen(
    navigateBack: () -> Unit = {},
    navigateToChooseTimeAndMaybeColor: (units: String?, isEstimate: Boolean, dose: Double?, estimatedDoseStandardDeviation: Double?) -> Unit,
    navigateToVolumetricDosingScreenOnJournalTab: () -> Unit,
    navigateToSaferSniffingScreen: () -> Unit,
    navigateToCreateCustomUnit: () -> Unit,
    viewModel: ChooseDoseViewModel = hiltViewModel()
) {
    ChooseDoseScreen(
        navigateBack = navigateBack,
        navigateToVolumetricDosingScreen = navigateToVolumetricDosingScreenOnJournalTab,
        navigateToSaferSniffingScreen = navigateToSaferSniffingScreen,
        substanceName = viewModel.substance.name,
        roaDose = viewModel.roaDose,
        administrationRoute = viewModel.administrationRoute,
        doseText = viewModel.doseText,
        doseRemark = viewModel.substance.dosageRemark,
        onChangeDoseText = viewModel::onDoseTextChange,
        estimatedDoseStandardDeviationText = viewModel.estimatedDoseStandardDeviationText,
        onChangeEstimatedDoseStandardDeviationText = viewModel::onEstimatedDoseStandardDeviationChange,
        isValidDose = viewModel.isValidDose,
        isEstimate = viewModel.isEstimate,
        onChangeIsEstimate = {
            viewModel.isEstimate = it
        },
        navigateToNext = {
            navigateToChooseTimeAndMaybeColor(
                viewModel.units,
                viewModel.isEstimate,
                viewModel.dose,
                viewModel.estimatedDoseStandardDeviation
            )
        },
        useUnknownDoseAndNavigate = {
            navigateToChooseTimeAndMaybeColor(
                viewModel.units,
                false,
                null,
                null
            )
        },
        currentDoseClass = viewModel.currentDoseClass,
        purityText = viewModel.purityText,
        onPurityChange = {
            viewModel.purityText = it
        },
        isValidPurity = viewModel.isPurityValid,
        convertedDoseAndUnitText = viewModel.impureDoseWithUnit,
        isShowingUnitsField = viewModel.roaDose?.units?.isBlank() ?: true,
        units = viewModel.units,
        onChangeOfUnits = { viewModel.units = it },
        navigateToCreateCustomUnit = navigateToCreateCustomUnit
    )
}

@Preview
@Composable
fun ChooseDoseScreenPreview(
    @PreviewParameter(RoaDosePreviewProvider::class) roaDose: RoaDose,
) {
    ChooseDoseScreen(
        navigateToVolumetricDosingScreen = {},
        navigateToSaferSniffingScreen = {},
        substanceName = "Example substance",
        roaDose = roaDose,
        administrationRoute = AdministrationRoute.INSUFFLATED,
        doseRemark = "This is a dose remark",
        doseText = "5",
        onChangeDoseText = {},
        estimatedDoseStandardDeviationText = "",
        onChangeEstimatedDoseStandardDeviationText = {},
        isValidDose = true,
        isEstimate = false,
        onChangeIsEstimate = {},
        navigateToNext = {},
        useUnknownDoseAndNavigate = {},
        currentDoseClass = DoseClass.THRESHOLD,
        purityText = "20",
        onPurityChange = {},
        isValidPurity = true,
        convertedDoseAndUnitText = "25 impure mg",
        isShowingUnitsField = false,
        units = "mg",
        onChangeOfUnits = {},
        navigateToCreateCustomUnit = {},
    )
}

@Preview
@Composable
fun ChooseDoseScreenPreview2() {
    ChooseDoseScreen(
        navigateToVolumetricDosingScreen = {},
        navigateToSaferSniffingScreen = {},
        substanceName = "Example Substance",
        roaDose = null,
        administrationRoute = AdministrationRoute.ORAL,
        doseRemark = null,
        doseText = "5",
        onChangeDoseText = {},
        estimatedDoseStandardDeviationText = "",
        onChangeEstimatedDoseStandardDeviationText = {},
        isValidDose = true,
        isEstimate = false,
        onChangeIsEstimate = {},
        navigateToNext = {},
        useUnknownDoseAndNavigate = {},
        currentDoseClass = null,
        purityText = "20",
        onPurityChange = {},
        isValidPurity = true,
        convertedDoseAndUnitText = "25 impure mg",
        isShowingUnitsField = false,
        units = "mg",
        onChangeOfUnits = {},
        navigateToCreateCustomUnit = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDoseScreen(
    navigateBack: () -> Unit = {},
    navigateToVolumetricDosingScreen: () -> Unit,
    navigateToSaferSniffingScreen: () -> Unit,
    substanceName: String,
    roaDose: RoaDose?,
    administrationRoute: AdministrationRoute,
    doseRemark: String?,
    doseText: String,
    onChangeDoseText: (String) -> Unit,
    estimatedDoseStandardDeviationText: String,
    onChangeEstimatedDoseStandardDeviationText: (String) -> Unit,
    isValidDose: Boolean,
    isEstimate: Boolean,
    onChangeIsEstimate: (Boolean) -> Unit,
    navigateToNext: () -> Unit,
    useUnknownDoseAndNavigate: () -> Unit,
    currentDoseClass: DoseClass?,
    purityText: String,
    onPurityChange: (purity: String) -> Unit,
    isValidPurity: Boolean,
    convertedDoseAndUnitText: String?,
    isShowingUnitsField: Boolean,
    units: String,
    onChangeOfUnits: (units: String) -> Unit,
    navigateToCreateCustomUnit: () -> Unit,
) {
    BackHandler(onBack = navigateBack)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.journal_dose_title_format, substanceName, stringResource(administrationRoute.getLocalizedNameRes()))) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.journal_action_back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (isValidDose) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.imePadding(),
                    onClick = navigateToNext,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.NavigateNext,
                            contentDescription = stringResource(R.string.journal_action_next)
                        )
                    },
                    text = { Text(stringResource(R.string.journal_action_next)) },
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            LinearProgressIndicator(
                progress = { 0.67f },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(4.dp))
            ElevatedCard(
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = 10.dp
                    )
                ) {
                    var isDoseRemarkExpanded by remember { mutableStateOf(false) }
                    Row(modifier = Modifier.clickable {
                        isDoseRemarkExpanded = !isDoseRemarkExpanded
                    }, verticalAlignment = Alignment.CenterVertically) {
                        val finalDoseInfoText =
                            if (doseRemark.isNullOrBlank()) DOSE_DISCLAIMER else "$doseRemark\n\n$DOSE_DISCLAIMER"
                        Text(
                            modifier = Modifier.weight(1f),
                            text = finalDoseInfoText,
                            maxLines = if (isDoseRemarkExpanded) Int.MAX_VALUE else 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!isDoseRemarkExpanded) {
                            Icon(
                                imageVector = Icons.Default.Expand,
                                contentDescription = stringResource(R.string.journal_action_expand)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    if (roaDose != null) {
                        RoaDoseView(roaDose = roaDose)
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = stringResource(R.string.journal_dosage_warning)
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(
                                text = stringResource(R.string.journal_no_dose_info_for_route),
                            )
                        }
                    }
                    OptionalDosageUnitDisclaimer(substanceName)
                }
            }
            ElevatedCard(
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = 10.dp
                    )
                ) {
                    if (roaDose != null) {
                        AnimatedVisibility(visible = currentDoseClass != null) {
                            if (currentDoseClass != null) {
                                CurrentDoseClassInfo(currentDoseClass, roaDose)
                            }
                        }
                    }
                    val focusManager = LocalFocusManager.current
                    val focusRequester = remember { FocusRequester() }
                    val textStyle = MaterialTheme.typography.titleMedium
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                    OutlinedTextField(
                        value = doseText,
                        onValueChange = {
                            onChangeDoseText(
                                it.replace(
                                    oldChar = ',',
                                    newChar = '.'
                                )
                            )
                        },
                        textStyle = textStyle,
                        label = { Text(stringResource(R.string.journal_dose_pure), style = textStyle) },
                        isError = !isValidDose,
                        trailingIcon = {
                            Text(
                                text = units,
                                style = textStyle,
                                modifier = Modifier.padding(horizontal = horizontalPadding)
                            )
                        },
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                    if (isShowingUnitsField) {
                        OutlinedTextField(
                            value = units,
                            onValueChange = onChangeOfUnits,
                            label = { Text(stringResource(R.string.journal_dose_units)) },
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(onClick = { onChangeOfUnits("µg") }) {
                                Text(text = "µg")
                            }
                            OutlinedButton(onClick = { onChangeOfUnits("mg") }) {
                                Text(text = "mg")
                            }
                            OutlinedButton(onClick = { onChangeOfUnits("g") }) {
                                Text(text = "g")
                            }
                            OutlinedButton(onClick = { onChangeOfUnits("mL") }) {
                                Text(text = "mL")
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Switch(checked = isEstimate, onCheckedChange = onChangeIsEstimate)
                        Text(stringResource(R.string.journal_dose_estimate), style = MaterialTheme.typography.titleMedium)
                    }
                    AnimatedVisibility(visible = isEstimate) {
                        Column {
                            OutlinedTextField(
                                value = estimatedDoseStandardDeviationText,
                                onValueChange = {
                                    onChangeEstimatedDoseStandardDeviationText(
                                        it.replace(
                                            oldChar = ',',
                                            newChar = '.'
                                        )
                                    )
                                },
                                textStyle = textStyle,
                                label = { Text(stringResource(R.string.journal_dose_estimated_sd), style = textStyle) },
                                trailingIcon = {
                                    Text(
                                        text = units,
                                        style = textStyle,
                                        modifier = Modifier.padding(horizontal = horizontalPadding)
                                    )
                                },
                                keyboardActions = KeyboardActions(onDone = {
                                    focusManager.clearFocus()
                                }),
                                isError = estimatedDoseStandardDeviationText.toDoubleOrNull() == null,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                            val mean = doseText.toDoubleOrNull()
                            val standardDeviation = estimatedDoseStandardDeviationText.toDoubleOrNull()
                            val isExplanationShown = mean != null && standardDeviation != null
                            AnimatedVisibility(isExplanationShown) {
                                if (mean != null && standardDeviation != null) {
                                    StandardDeviationExplanation(
                                        mean = mean,
                                        standardDeviation = standardDeviation,
                                        unit = units
                                    )
                                }
                            }
                        }
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = horizontalPadding,
                            vertical = 10.dp
                        )
                        .fillMaxWidth()
                ) {
                    if (substanceName == "Cannabis" && administrationRoute == AdministrationRoute.SMOKED) {
                        Text(stringResource(R.string.journal_custom_unit_cannabis_prompt))
                    } else if (substanceName == "Psilocybin mushrooms") {
                        Text(stringResource(R.string.journal_custom_unit_shrooms_prompt))
                    } else if (substanceName == "Alcohol") {
                        Text(stringResource(R.string.journal_custom_unit_alcohol_prompt))
                    } else if (substanceName == "Caffeine") {
                        Text(stringResource(R.string.journal_custom_unit_caffeine_prompt))
                    } else {
                        val unitSuggestions = when (administrationRoute) {
                            AdministrationRoute.ORAL -> stringResource(R.string.journal_custom_unit_oral_suggestions)
                            AdministrationRoute.SMOKED -> stringResource(R.string.journal_custom_unit_smoked_suggestions)
                            AdministrationRoute.INSUFFLATED -> stringResource(R.string.journal_custom_unit_insufflated_suggestions)
                            AdministrationRoute.BUCCAL -> stringResource(R.string.journal_custom_unit_buccal_suggestions)
                            AdministrationRoute.TRANSDERMAL -> stringResource(R.string.journal_custom_unit_transdermal_suggestions)
                            else -> stringResource(R.string.journal_custom_unit_other_suggestions)
                        }
                        Text(text = stringResource(R.string.journal_custom_unit_prompt_format, unitSuggestions))
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedButton(onClick = navigateToCreateCustomUnit) {
                        Text(stringResource(R.string.journal_create_custom_unit))
                    }
                }
            }
            var isShowingUnknownDoseDialog by remember { mutableStateOf(false) }
            TextButton(onClick = { isShowingUnknownDoseDialog = true }) {
                Text(text = stringResource(R.string.journal_log_unknown_dose))
            }
            AnimatedVisibility(visible = isValidDose) {
                ElevatedCard(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = horizontalPadding,
                            vertical = 10.dp
                        )
                    ) {
                        PurityCalculation(
                            purityText = purityText,
                            onPurityChange = onPurityChange,
                            convertedDoseAndUnitText = convertedDoseAndUnitText,
                            isValidPurity = isValidPurity
                        )
                    }
                }
            }
            AnimatedVisibility(visible = isShowingUnknownDoseDialog) {
                UnknownDoseDialog(
                    useUnknownDoseAndNavigate = useUnknownDoseAndNavigate,
                    dismiss = { isShowingUnknownDoseDialog = false }
                )
            }
            if (administrationRoute == AdministrationRoute.INSUFFLATED) {
                TextButton(onClick = navigateToSaferSniffingScreen) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.journal_action_info),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.journal_safer_sniffing))
                }
            } else if (administrationRoute == AdministrationRoute.RECTAL) {
                val uriHandler = LocalUriHandler.current
                TextButton(onClick = { uriHandler.openUri(AdministrationRoute.SAFER_PLUGGING_ARTICLE_URL) }) {
                    Icon(
                        Icons.Outlined.OpenInBrowser,
                        contentDescription = stringResource(R.string.journal_action_open_link)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.journal_safer_plugging))
                }
            }
            if (roaDose?.shouldUseVolumetricDosing == true) {
                TextButton(onClick = navigateToVolumetricDosingScreen) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.journal_action_info),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.journal_volumetric_liquid_dosing))
                }
            }
            if (administrationRoute == AdministrationRoute.SMOKED && substanceName != "Cannabis") {
                ElevatedCard(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = 4.dp
                    )
                ) {
                    ChasingTheDragonText(
                        modifier = Modifier.padding(
                            horizontal = horizontalPadding,
                            vertical = 10.dp
                        )
                    )
                }
            }
        }
    }
}