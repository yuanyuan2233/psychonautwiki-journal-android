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

package com.isaakhanimann.journal.ui.tabs.journal.addingestion.route

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.substances.AdministrationRoute
import com.isaakhanimann.journal.ui.tabs.journal.getLocalizedDescRes
import com.isaakhanimann.journal.ui.tabs.journal.getLocalizedNameRes

@Composable
fun ChooseRouteScreen(
    navigateBack: () -> Unit = {},
    navigateToChooseDose: (administrationRoute: AdministrationRoute) -> Unit,
    navigateToRouteExplanationScreen: () -> Unit,
    viewModel: ChooseRouteViewModel = hiltViewModel()
) {
    ChooseRouteScreen(
        showOtherRoutes = viewModel.showOtherRoutes,
        onChangeOfShowOtherRoutes = {
            viewModel.showOtherRoutes = it
        },
        pwRoutes = viewModel.pwRoutes,
        otherRoutesChunked = viewModel.otherRoutesChunked,
        onRouteTapped = { route ->
            if (route.isInjectionMethod) {
                viewModel.currentRoute = route
                viewModel.isShowingInjectionDialog = true
            } else {
                navigateToChooseDose(route)
            }
        },
        navigateBack = navigateBack,
        navigateToRouteExplanationScreen = navigateToRouteExplanationScreen,
        isShowingInjectionDialog = viewModel.isShowingInjectionDialog,
        navigateWithCurrentRoute = {
            navigateToChooseDose(viewModel.currentRoute)
        },
        dismissInjectionDialog = {
            viewModel.isShowingInjectionDialog = false
        },
        substanceName = viewModel.substanceName
    )
}

@Preview
@Composable
fun ChooseRouteScreenPreview() {
    val pwRoutes = listOf(AdministrationRoute.INSUFFLATED, AdministrationRoute.ORAL)
    val otherRoutes = AdministrationRoute.entries.filter { route ->
        !pwRoutes.contains(route)
    }
    val otherRoutesChunked = otherRoutes.chunked(2)
    ChooseRouteScreen(
        showOtherRoutes = false,
        onChangeOfShowOtherRoutes = {},
        pwRoutes = pwRoutes,
        otherRoutesChunked = otherRoutesChunked,
        onRouteTapped = {},
        navigateToRouteExplanationScreen = {},
        isShowingInjectionDialog = false,
        navigateWithCurrentRoute = {},
        dismissInjectionDialog = {},
        substanceName = "LSD"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseRouteScreen(
    showOtherRoutes: Boolean,
    onChangeOfShowOtherRoutes: (Boolean) -> Unit,
    pwRoutes: List<AdministrationRoute>,
    otherRoutesChunked: List<List<AdministrationRoute>>,
    onRouteTapped: (route: AdministrationRoute) -> Unit,
    navigateBack: () -> Unit = {},
    navigateToRouteExplanationScreen: () -> Unit,
    isShowingInjectionDialog: Boolean,
    navigateWithCurrentRoute: () -> Unit,
    dismissInjectionDialog: () -> Unit,
    substanceName: String,
) {
    BackHandler(onBack = {
        if (showOtherRoutes && pwRoutes.isNotEmpty()) {
            onChangeOfShowOtherRoutes(false)
        } else {
            navigateBack()
        }
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.journal_substance_route, substanceName)) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (showOtherRoutes && pwRoutes.isNotEmpty()) {
                            onChangeOfShowOtherRoutes(false)
                        } else {
                            navigateBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.journal_action_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = navigateToRouteExplanationScreen) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.journal_routes_info)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier.fillMaxWidth(),
            )
            AnimatedVisibility(visible = isShowingInjectionDialog) {
                InjectionDialog(
                    navigateToNext = navigateWithCurrentRoute,
                    dismiss = dismissInjectionDialog
                )
            }
            AdministrationRoutePicker(
                showOtherRoutes = showOtherRoutes,
                onChangeOfShowOtherRoutes = onChangeOfShowOtherRoutes,
                pwRoutes = pwRoutes,
                otherRoutesChunked = otherRoutesChunked,
                onRouteTapped = onRouteTapped
            )
        }
    }
}

@Preview
@Composable
fun InjectionDialogPreview() {
    InjectionDialog(
        navigateToNext = {},
        dismiss = {}
    )
}

@Composable
fun InjectionDialog(
    navigateToNext: () -> Unit,
    dismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = stringResource(R.string.journal_action_warning))
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.journal_safer_injection), style = MaterialTheme.typography.headlineSmall)
            }
        },
        text = {
            Column {
                Text(stringResource(R.string.journal_injection_warning_1))
                Text(stringResource(R.string.journal_read_safer_injection_guide))
                SaferInjectionLink()
                Text(stringResource(R.string.journal_injection_warning_2))

            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    dismiss()
                    navigateToNext()
                }
            ) {
                Text(stringResource(R.string.journal_action_continue))
            }
        },
        dismissButton = {
            TextButton(
                onClick = dismiss
            ) {
                Text(stringResource(R.string.journal_action_cancel))
            }
        }
    )
}

@Composable
fun RouteBox(route: AdministrationRoute, titleStyle: TextStyle) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(route.getLocalizedNameRes()),
                textAlign = TextAlign.Center,
                style = titleStyle
            )
            if (screenHeightPx > 500) {
                Text(
                    text = stringResource(route.getLocalizedDescRes()),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
fun SaferInjectionLink() {
    val uriHandler = LocalUriHandler.current
    TextButton(onClick = {
        uriHandler.openUri(AdministrationRoute.SAFER_INJECTION_ARTICLE_URL)
    }) {
        Icon(
            Icons.Outlined.OpenInBrowser,
            contentDescription = stringResource(R.string.journal_action_open_link),
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(R.string.journal_safer_injection_guide))
    }
}

