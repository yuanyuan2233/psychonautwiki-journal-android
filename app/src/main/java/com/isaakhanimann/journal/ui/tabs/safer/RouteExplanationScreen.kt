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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
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
import com.isaakhanimann.journal.data.substances.AdministrationRoute
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionText
import com.isaakhanimann.journal.ui.tabs.search.substance.SectionWithTitle
import com.isaakhanimann.journal.ui.tabs.search.substance.VerticalSpace
import com.isaakhanimann.journal.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RouteExplanationScreen() {
    val uriHandler = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_routes_title)) },
                actions = {
                    TextButton(
                        onClick = { uriHandler.openUri(AdministrationRoute.PSYCHONAUT_WIKI_ARTICLE_URL) },
                    ) {
                        Text(stringResource(R.string.safer_action_article))
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
            ElevatedCard(
                modifier = Modifier.padding(
                    horizontal = horizontalPadding,
                    vertical = 3.dp
                )
            ) {
                Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
                    SectionText(
                        text = stringResource(R.string.safer_routes_intro)
                    )
                    VerticalSpace()
                }
            }
            AdministrationRoute.entries.filter { !it.isInjectionMethod }.forEach {
                SectionWithTitle(title = stringResource(it.getTitleRes())) {
                    Text(
                        text = stringResource(it.getArticleTextRes()),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .padding(bottom = 10.dp)
                    )
                    if (it == AdministrationRoute.RECTAL) {
                        Button(
                            onClick = { uriHandler.openUri(AdministrationRoute.SAFER_PLUGGING_ARTICLE_URL) },
                            modifier = Modifier.padding(horizontal = 5.dp)
                        ) {
                            Icon(
                                Icons.Outlined.OpenInBrowser,
                                contentDescription = stringResource(R.string.safer_cd_open_pw_article),
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text(stringResource(R.string.safer_btn_safer_plugging))
                        }
                    }
                }
            }
            AdministrationRoute.entries.filter { it.isInjectionMethod }.forEach {
                SectionWithTitle(title = stringResource(it.getTitleRes())) {
                    Text(
                        text = stringResource(it.getArticleTextRes()),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(horizontal = horizontalPadding)
                            .padding(bottom = 10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

private fun AdministrationRoute.getTitleRes(): Int = when (this) {
    AdministrationRoute.ORAL -> R.string.safer_route_oral_title
    AdministrationRoute.SUBLINGUAL -> R.string.safer_route_sublingual_title
    AdministrationRoute.BUCCAL -> R.string.safer_route_buccal_title
    AdministrationRoute.INSUFFLATED -> R.string.safer_route_insufflated_title
    AdministrationRoute.RECTAL -> R.string.safer_route_rectal_title
    AdministrationRoute.TRANSDERMAL -> R.string.safer_route_transdermal_title
    AdministrationRoute.SMOKED -> R.string.safer_route_smoked_title
    AdministrationRoute.INHALED -> R.string.safer_route_inhaled_title
    AdministrationRoute.SUBCUTANEOUS -> R.string.safer_route_subcutaneous_title
    AdministrationRoute.INTRAMUSCULAR -> R.string.safer_route_intramuscular_title
    AdministrationRoute.INTRAVENOUS -> R.string.safer_route_intravenous_title
}

private fun AdministrationRoute.getArticleTextRes(): Int = when (this) {
    AdministrationRoute.ORAL -> R.string.safer_route_oral_text
    AdministrationRoute.SUBLINGUAL -> R.string.safer_route_sublingual_text
    AdministrationRoute.BUCCAL -> R.string.safer_route_buccal_text
    AdministrationRoute.INSUFFLATED -> R.string.safer_route_insufflated_text
    AdministrationRoute.RECTAL -> R.string.safer_route_rectal_text
    AdministrationRoute.TRANSDERMAL -> R.string.safer_route_transdermal_text
    AdministrationRoute.SMOKED -> R.string.safer_route_smoked_text
    AdministrationRoute.INHALED -> R.string.safer_route_inhaled_text
    AdministrationRoute.SUBCUTANEOUS -> R.string.safer_route_subcutaneous_text
    AdministrationRoute.INTRAMUSCULAR -> R.string.safer_route_intramuscular_text
    AdministrationRoute.INTRAVENOUS -> R.string.safer_route_intravenous_text
}