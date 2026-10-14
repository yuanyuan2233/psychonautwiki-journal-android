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

package com.isaakhanimann.journal.ui.tabs.journal.experience.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.journal.experience.components.CardWithTitle
import com.isaakhanimann.journal.ui.tabs.search.substance.BulletPoints
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionText
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ExplainTimelineScreen(navigateBack: (() -> Unit)? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.journal_timeline_info_title)) },
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
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = horizontalPadding)
        ) {
            VerticalSpace()
            CardWithTitle(title = stringResource(R.string.journal_timeline_assumptions_title)) {
                val text = buildAnnotatedString {
                    append(stringResource(R.string.journal_timeline_assumptions_intro_1))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                        append(stringResource(R.string.journal_timeline_assumptions_intro_bold))
                    }
                    append(stringResource(R.string.journal_timeline_assumptions_intro_2))
                }
                SectionText(text = text)
                BulletPoints(
                    points = listOf(
                        stringResource(R.string.journal_timeline_assumption_1),
                        stringResource(R.string.journal_timeline_assumption_2),
                        stringResource(R.string.journal_timeline_assumption_3),
                        stringResource(R.string.journal_timeline_assumption_4),
                    )
                )
            }
            CardWithTitle(title = stringResource(R.string.journal_timeline_understanding_title)) {
                BulletPoints(
                    points = listOf(
                        stringResource(R.string.journal_timeline_understanding_1),
                        stringResource(R.string.journal_timeline_understanding_2),
                        stringResource(R.string.journal_timeline_understanding_3),
                        stringResource(R.string.journal_timeline_understanding_4),
                        stringResource(R.string.journal_timeline_understanding_5),
                        stringResource(R.string.journal_timeline_understanding_6),
                        stringResource(R.string.journal_timeline_understanding_7),
                    )
                )
            }
            CardWithTitle(title = stringResource(R.string.journal_timeline_pw_durations_title)) {
                SectionText(
                    text = stringResource(R.string.journal_timeline_pw_durations_desc)
                )
                val titleStyle = MaterialTheme.typography.titleSmall
                Text(text = stringResource(R.string.journal_phase_total), style = titleStyle)
                SectionText(text = stringResource(R.string.journal_phase_total_desc))
                Text(text = stringResource(R.string.journal_phase_onset), style = titleStyle)
                SectionText(text = stringResource(R.string.journal_phase_onset_desc))
                Text(text = stringResource(R.string.journal_phase_comeup), style = titleStyle)
                SectionText(text = stringResource(R.string.journal_phase_comeup_desc))
                Text(text = stringResource(R.string.journal_phase_peak), style = titleStyle)
                SectionText(text = stringResource(R.string.journal_phase_peak_desc))
                Text(text = stringResource(R.string.journal_phase_offset), style = titleStyle)
                SectionText(text = stringResource(R.string.journal_phase_offset_desc))
                Text(text = stringResource(R.string.journal_phase_afterglow), style = titleStyle)
                SectionText(
                    text = stringResource(R.string.journal_phase_afterglow_desc)
                )
            }
        }
    }
}

