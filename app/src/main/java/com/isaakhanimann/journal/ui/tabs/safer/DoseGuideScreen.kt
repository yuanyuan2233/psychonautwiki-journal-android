/*
 * Copyright (c) 2022. Isaak Hanimann.
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isaakhanimann.journal.R
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionText
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@Preview
@Composable
fun DoseGuideScreenPreview() {
    DoseGuideScreen(
        navigateToDoseClassification = {},
        navigateToVolumetricDosing = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoseGuideScreen(
    navigateToDoseClassification: () -> Unit,
    navigateToVolumetricDosing: () -> Unit,
    navigateBack: () -> Unit = {}
) {
    BackHandler(onBack = navigateBack)
    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_dose_guide_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.journal_action_back)
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { uriHandler.openUri("https://psychonautwiki.org/wiki/Dosage") },
                    ) {
                        Text(stringResource(R.string.safer_action_article))
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = horizontalPadding)
        ) {

            SectionText(
                text = stringResource(R.string.safer_dose_guide_overview_text)
            )
            val titleStyle = MaterialTheme.typography.titleMedium
            Text(stringResource(R.string.safer_dose_guide_choosing_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_choosing_text)
            )
            Text(stringResource(R.string.safer_dose_guide_allergy_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_allergy_text)
            )
            Text(stringResource(R.string.safer_dose_guide_measurement_title), style = titleStyle)
            SectionText(text = stringResource(R.string.safer_dose_guide_measurement_text))
            Text(stringResource(R.string.safer_dose_guide_eyeballing_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_eyeballing_text)
            )
            Text(stringResource(R.string.safer_dose_guide_scales_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_scales_text)
            )
            Text(stringResource(R.string.safer_dose_guide_technique_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_technique_text)
            )
            Text(stringResource(R.string.safer_dose_guide_volumetric_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_volumetric_text)
            )
            Button(
                onClick = navigateToVolumetricDosing,
                modifier = Modifier.padding(horizontal = horizontalPadding)
            ) {
                Text(text = stringResource(R.string.safer_btn_volumetric_dosing))
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.safer_dose_guide_classification_title), style = titleStyle)
            SectionText(
                text = stringResource(R.string.safer_dose_guide_classification_text)
            )
            Button(
                onClick = navigateToDoseClassification,
                modifier = Modifier.padding(horizontal = horizontalPadding)
            ) {
                Text(text = stringResource(R.string.safer_btn_dosage_classification))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}