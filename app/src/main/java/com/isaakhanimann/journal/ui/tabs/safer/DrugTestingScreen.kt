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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Biotech
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.theme.horizontalPadding
import com.isaakhanimann.journal.ui.theme.minimumTouchTargetHeight

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DrugTestingScreen(navigateBack: () -> Unit = {}) {
    BackHandler(onBack = navigateBack)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.safer_drug_testing_title)) },
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
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = horizontalPadding)
        ) {
            CardWithTesting(title = stringResource(R.string.safer_country_austria)) {
                TestingServiceItem(
                    name = "Drogenarbeit Z6",
                    city = stringResource(R.string.safer_city_innsbruck),
                    url = "https://www.drogenarbeitz6.at/drug-checking.html"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Checkit!",
                    city = stringResource(R.string.safer_city_vienna),
                    url = "https://checkit.wien/drug-checking-2/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Triptalks",
                    city = stringResource(R.string.safer_city_graz),
                    url = "https://triptalks.at"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_australia)) {
                TestingServiceItem(
                    name = "CanTEST",
                    city = stringResource(R.string.safer_city_canberra),
                    url = "https://www.cahma.org.au/services/cantest/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "CheQpoint",
                    city = stringResource(R.string.safer_city_queensland),
                    url = "https://www.quihn.org/cheqpoint/"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_belgium)) {
                TestingServiceItem(
                    name = "Modus Vivendi",
                    city = stringResource(R.string.safer_city_saint_gilles),
                    url = "https://www.modusvivendi-be.org"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Exaequo @ Rainbowhouse",
                    city = stringResource(R.string.safer_city_brussels),
                    url = "https://www.exaequo.be"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_canada)) {
                TestingServiceItem(
                    name = "Get Your Drugs Tested",
                    city = stringResource(R.string.safer_city_vancouver),
                    url = "http://www.vch.ca/public-health/harm-reduction/overdose-prevention-response/drug-checking"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "grip",
                    city = stringResource(R.string.safer_city_montreal),
                    url = "https://grip-prevention.ca/en/drug-checking/"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_germany)) {
                TestingServiceItem(
                    name = "Drugchecking",
                    city = stringResource(R.string.safer_city_berlin),
                    url = "https://drugchecking.berlin"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_france)) {
                TestingServiceItem(
                    name = "Asso Michel - CAARUD Médiane",
                    city = stringResource(R.string.safer_city_dunkerque),
                    url = "https://www.associationmichel.com/caarud-mediane-722/le-caarud-mediane-743/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Le MAS - CAARUD Pause diabolo",
                    city = stringResource(R.string.safer_city_lyon),
                    url = "https://www.mas-asso.fr/service/pause-diabolo/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Centre \"Les Wads\"",
                    city = stringResource(R.string.safer_city_metz),
                    url = "http://www.leswadscmsea.fr"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_italy)) {
                TestingServiceItem(
                    name = "Neutravel Project",
                    city = stringResource(R.string.safer_city_torino),
                    url = "https://www.neutravel.net/drug-checking"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_netherlands)) {
                TestingServiceItem(
                    name = "Drugs-test",
                    city = stringResource(R.string.safer_city_33_locations),
                    url = "https://www.drugs-test.nl/en/testlocations/"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_norway)) {
                TestingServiceItem(
                    name = "Rusopplysningen",
                    city = stringResource(R.string.safer_city_oslo),
                    url = "https://rusopplysningen.no/analysetjeneste"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_slovenia)) {
                TestingServiceItem(
                    name = "DrogArt",
                    city = stringResource(R.string.safer_city_various_locations),
                    url = "https://www.drogart.org/testirne-tocke/"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_spain)) {
                TestingServiceItem(
                    name = "Energy Control",
                    city = stringResource(R.string.safer_city_various_locations),
                    url = "https://energycontrol.org/servicio-de-analisis/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "CHEMSAFE",
                    city = stringResource(R.string.safer_city_various_locations),
                    url = "https://www.chem-safe.org/check-your-chems/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Kykeon Analytics",
                    city = stringResource(R.string.safer_city_various_locations),
                    url = "https://www.kykeonanalytics.com"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Ai Laket",
                    city = stringResource(R.string.safer_city_spain_ailaket),
                    url = "https://ailaket.com/proyectos/punto-fijo/"
                )
            }
            CardWithTesting(title = stringResource(R.string.safer_country_switzerland)) {
                TestingServiceItem(
                    name = "DIBS / Safer Dance Basel",
                    city = stringResource(R.string.safer_city_basel),
                    url = "https://de.saferdancebasel.ch/drugchecking"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "DIB / rave it safe",
                    city = stringResource(R.string.safer_city_bern_biel),
                    url = "https://www.raveitsafe.ch/angebotsdetails/dib-drug-checking-bern/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "Nuit Blanche",
                    city = stringResource(R.string.safer_city_geneva),
                    url = "https://nuit-blanche.ch/drug-checking/"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "DIZ / Saferparty",
                    city = stringResource(R.string.safer_city_zurich),
                    url = "https://en.saferparty.ch/angebote/drug-checking"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "DILU Luzern",
                    city = stringResource(R.string.safer_city_luzern),
                    url = "https://www.gassenarbeit.ch/angebote/dilu"
                )
                HorizontalDivider()
                TestingServiceItem(
                    name = "NightLife Vaud",
                    city = stringResource(R.string.safer_city_lausanne),
                    url = "https://nightlifevaud.ch/permanence/"
                )
            }

            CardWithTesting(title = stringResource(R.string.safer_country_united_kingdom)) {
                TestingServiceItem(
                    name = "The Loop",
                    city = stringResource(R.string.safer_city_bristol),
                    url = "https://wearetheloop.org"
                )
            }

            val uriHandler = LocalUriHandler.current
            val reportServiceText = stringResource(R.string.safer_drug_testing_report_missing)
            TextButton(
                onClick = {
                    uriHandler.openUri("https://t.me/isaakhanimann")
                },
                modifier = Modifier.semantics {
                    contentDescription = reportServiceText
                }) {
                Text(
                    reportServiceText,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
fun CardWithTesting(
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(modifier = Modifier.padding(vertical = 5.dp)) {
        Column(
            Modifier.padding(vertical = 5.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider()
            content()
        }
    }
}

@Preview
@Composable
fun TestingServiceItemPreview() {
    TestingServiceItem(
        name = "DIZ / Saferparty",
        city = "Zurich",
        url = "https://en.saferparty.ch/angebote/drug-checking"
    )
}

@Composable
fun TestingServiceItem(
    name: String,
    city: String,
    url: String
) {
    val uriHandler = LocalUriHandler.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .clickable {
                uriHandler.openUri(url)
            }
            .padding(horizontal = horizontalPadding, vertical = 5.dp)
            .heightIn(min = minimumTouchTargetHeight)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Outlined.Biotech,
            contentDescription = stringResource(R.string.safer_cd_open_link)
        )
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = city,
            maxLines = 1,
            modifier = Modifier.width(IntrinsicSize.Max)
        )
    }
}
