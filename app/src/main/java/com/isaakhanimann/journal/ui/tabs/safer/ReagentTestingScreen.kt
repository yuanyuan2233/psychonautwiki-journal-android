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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionText
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ReagentTestingScreen() {
    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_reagent_testing_title)) },
                actions = {
                    TextButton(
                        onClick = { uriHandler.openUri("https://psychonautwiki.org/wiki/Reagent_testing_kits") },
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
            ElevatedCard(modifier = Modifier.padding(vertical = 3.dp)) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_reagent_testing_text)
                    )
                    VerticalSpace()
                }
            }
            ElevatedCard(modifier = Modifier.padding(vertical = 5.dp)) {
                Column(
                    Modifier.padding(vertical = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.safer_reagent_kit_sellers_title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    HorizontalDivider()
                    TextButton(onClick = { uriHandler.openUri("https://dancesafe.org/testing-kit-instructions/") }) {
                        Text(text = "DanceSafe")
                    }
                    HorizontalDivider()
                    TextButton(onClick = { uriHandler.openUri("https://bunkpolice.com") }) {
                        Text(text = "Bunk Police")
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}