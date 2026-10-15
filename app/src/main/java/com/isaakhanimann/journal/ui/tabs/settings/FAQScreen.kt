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

package com.isaakhanimann.journal.ui.tabs.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.stringResource
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionWithTitle
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FAQScreen(navigateBack: () -> Unit = {}) {
    BackHandler(onBack = navigateBack)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_faq_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.journal_action_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            QuestionAnswerRow(
                question = stringResource(R.string.settings_faq_q_sources),
                answer = stringResource(R.string.settings_faq_a_sources)
            )
            QuestionAnswerRow(
                question = stringResource(R.string.settings_faq_q_interactions),
                answer = stringResource(R.string.settings_faq_a_interactions)
            )
            QuestionAnswerRow(
                question = stringResource(R.string.settings_faq_q_edit_info),
                answer = stringResource(R.string.settings_faq_a_edit_info)
            )
            QuestionAnswerRow(
                question = stringResource(R.string.settings_faq_q_timeline_independent),
                answer = stringResource(R.string.settings_faq_a_timeline_independent)
            )
            QuestionAnswerRow(
                question = stringResource(R.string.settings_faq_q_dots_meaning),
                answer = stringResource(R.string.settings_faq_a_dots_meaning)
            )
        }
    }
}

@Composable
fun QuestionAnswerRow(question: String, answer: String) {
    SectionWithTitle(title = question) {
        Text(
            text = answer,
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .padding(top = 5.dp, bottom = 8.dp)
        )
    }
}


