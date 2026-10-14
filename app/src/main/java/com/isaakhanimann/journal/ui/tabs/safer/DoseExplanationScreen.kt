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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.substances.classes.roa.DoseClass
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionWithTitle
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DoseExplanationScreen(navigateBack: (() -> Unit)? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_dose_explanation_title)) },
                navigationIcon = {
                    if (navigateBack != null) {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.journal_action_back)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            VerticalSpace()
            ElevatedCard(modifier = Modifier.padding(vertical = 5.dp, horizontal = horizontalPadding)) {
                Text(
                    text = stringResource(R.string.safer_dose_explanation_intro),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = horizontalPadding)
                )
            }
            DoseClass.entries.forEach {
                SectionWithTitle(title = stringResource(it.getTitleRes())) {
                    Text(
                        text = stringResource(it.getDescriptionRes()),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .padding(horizontal = horizontalPadding)
                    )
                }
            }
        }
    }
}

private fun DoseClass.getTitleRes(): Int = when (this) {
    DoseClass.THRESHOLD -> R.string.safer_dose_class_threshold_title
    DoseClass.LIGHT -> R.string.safer_dose_class_light_title
    DoseClass.COMMON -> R.string.safer_dose_class_common_title
    DoseClass.STRONG -> R.string.safer_dose_class_strong_title
    DoseClass.HEAVY -> R.string.safer_dose_class_heavy_title
}

private fun DoseClass.getDescriptionRes(): Int = when (this) {
    DoseClass.THRESHOLD -> R.string.safer_dose_class_threshold_desc
    DoseClass.LIGHT -> R.string.safer_dose_class_light_desc
    DoseClass.COMMON -> R.string.safer_dose_class_common_desc
    DoseClass.STRONG -> R.string.safer_dose_class_strong_desc
    DoseClass.HEAVY -> R.string.safer_dose_class_heavy_desc
}