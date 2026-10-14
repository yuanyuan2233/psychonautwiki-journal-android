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
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.search.substance.BulletPoints
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionText
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionWithTitle
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SaferHallucinogensScreen(navigateBack: (() -> Unit)? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_hallucinogens_title)) },
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
        ) {
            ElevatedCard(modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 3.dp)) {
                Text(
                    text = stringResource(R.string.safer_hallucinogens_intro),
                    modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 5.dp)
                )
                VerticalSpace()
            }
            SectionWithTitle(title = stringResource(R.string.safer_hallucinogens_setting_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(text = stringResource(R.string.safer_hallucinogens_setting_intro))
                    BulletPoints(
                        points = listOf(
                            stringResource(R.string.safer_hallucinogens_setting_p1),
                            stringResource(R.string.safer_hallucinogens_setting_p2),
                            stringResource(R.string.safer_hallucinogens_setting_p3),
                            stringResource(R.string.safer_hallucinogens_setting_p4),
                        ),
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    SectionText(text = stringResource(R.string.safer_hallucinogens_setting_outro))
                    VerticalSpace()
                }
            }
            SectionWithTitle(title = stringResource(R.string.safer_hallucinogens_set_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_hallucinogens_set_text)
                    )
                    VerticalSpace()
                }
            }
            SectionWithTitle(title = stringResource(R.string.safer_hallucinogens_body_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_hallucinogens_body_text)
                    )
                    VerticalSpace()
                }
            }
            SectionWithTitle(title = stringResource(R.string.safer_hallucinogens_sitters_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_hallucinogens_sitters_text)
                    )
                    VerticalSpace()
                }
            }
            SectionWithTitle(stringResource(R.string.safer_hallucinogens_anchors_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(text = stringResource(R.string.safer_hallucinogens_anchors_intro))
                    SectionText(text = stringResource(R.string.safer_hallucinogens_anchors_examples_intro))
                    BulletPoints(
                        points = listOf(
                            stringResource(R.string.safer_hallucinogens_anchors_p1),
                            stringResource(R.string.safer_hallucinogens_anchors_p2),
                            stringResource(R.string.safer_hallucinogens_anchors_p3),
                            stringResource(R.string.safer_hallucinogens_anchors_p4),
                            stringResource(R.string.safer_hallucinogens_anchors_p5)
                        ),
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    VerticalSpace()
                }
            }
            SectionWithTitle(stringResource(R.string.safer_hallucinogens_abort_title)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_hallucinogens_abort_text)
                    )
                    VerticalSpace()
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}