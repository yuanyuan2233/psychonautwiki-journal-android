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

package com.isaakhanimann.journal.ui.tabs.safer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionWithTitle
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding
import com.isaakhanimann.journal.ui.theme.verticalPaddingCards

@Preview
@Composable
fun SaferUsePreview() {
    SaferUseScreen(
        navigateToDrugTestingScreen = {},
        navigateToSaferHallucinogensScreen = {},
        navigateToVolumetricDosingScreen = {},
        navigateToDosageGuideScreen = {},
        navigateToDosageClassificationScreen = {},
        navigateToRouteExplanationScreen = {},
        navigateToReagentTestingScreen = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaferUseScreen(
    navigateToDrugTestingScreen: () -> Unit,
    navigateToSaferHallucinogensScreen: () -> Unit,
    navigateToVolumetricDosingScreen: () -> Unit,
    navigateToDosageGuideScreen: () -> Unit,
    navigateToDosageClassificationScreen: () -> Unit,
    navigateToRouteExplanationScreen: () -> Unit,
    navigateToReagentTestingScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_use_title)) },
            )
        },
    ) { padding ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            SectionWithTitle(title = stringResource(R.string.safer_section_research_title)) {
                SaferText(text = stringResource(R.string.safer_section_research_text))
            }
            val uriHandler = LocalUriHandler.current
            SectionWithTitle(title = stringResource(R.string.safer_section_testing_title)) {
                SaferText(text = stringResource(R.string.safer_section_testing_text))
                Button(
                    onClick = navigateToDrugTestingScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Outlined.Biotech,
                        contentDescription = stringResource(R.string.safer_cd_open_link),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_drug_testing))
                }
                Button(
                    onClick = navigateToReagentTestingScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Outlined.Science,
                        contentDescription = stringResource(R.string.safer_cd_open_link),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_reagent_testing))
                }
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_dosage_title)) {
                SaferText(text = stringResource(R.string.safer_section_dosage_text))
                Button(
                    onClick = navigateToDosageGuideScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Text(stringResource(R.string.safer_btn_dosage_guide))
                }
                Button(
                    onClick = navigateToDosageClassificationScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Text(stringResource(R.string.safer_btn_dosage_classification))
                }
                Button(
                    onClick = navigateToVolumetricDosingScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Text(stringResource(R.string.safer_btn_volumetric_dosing))
                }
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_set_setting_title)) {
                SaferText(text = stringResource(R.string.safer_section_set_setting_text))
                Button(
                    onClick = navigateToSaferHallucinogensScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Text(stringResource(R.string.safer_btn_safer_hallucinogens))
                }
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_combinations_title)) {
                SaferText(text = stringResource(R.string.safer_section_combinations_text))
                Button(
                    onClick = {
                        uriHandler.openUri("https://combi-checker.ch")
                    },
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Default.OpenInBrowser,
                        contentDescription = stringResource(R.string.safer_cd_open_link),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_swiss_combi))
                }
                Button(
                    onClick = {
                        uriHandler.openUri("https://combo.tripsit.me")
                    },
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Default.OpenInBrowser,
                        contentDescription = stringResource(R.string.safer_cd_open_link),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_tripsit_combi))
                }
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_routes_title)) {
                SaferText(text = stringResource(R.string.safer_section_routes_text))
                Button(
                    onClick = navigateToRouteExplanationScreen,
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.safer_cd_info),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_routes_info))
                }
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_allergy_title)) {
                SaferText(text = stringResource(R.string.safer_section_allergy_text))
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_reflection_title)) {
                SaferText(text = stringResource(R.string.safer_section_reflection_text))
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_safety_others_title)) {
                SaferText(text = stringResource(R.string.safer_section_safety_others_text))
            }
            SectionWithTitle(title = stringResource(R.string.safer_section_recovery_title)) {
                SaferText(text = stringResource(R.string.safer_section_recovery_text))
                Button(
                    onClick = {
                        uriHandler.openUri("https://www.youtube.com/watch?v=dv3agW-DZ5I")
                    },
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.safer_cd_open_link),
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_recovery_position_video))
                }
                VerticalSpace()
            }
            ElevatedCard(
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPaddingCards
                )
            ) {
                TextButton(
                    onClick = {
                        uriHandler.openUri("https://psychonautwiki.org/wiki/Responsible_drug_use")
                    },
                    modifier = Modifier.padding(horizontal = horizontalPadding)
                ) {
                    Icon(
                        Icons.Outlined.HealthAndSafety,
                        contentDescription = stringResource(R.string.safer_cd_responsible_use),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.safer_btn_responsible_use_article))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun SaferText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Left,
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .padding(bottom = 10.dp)
    )
}