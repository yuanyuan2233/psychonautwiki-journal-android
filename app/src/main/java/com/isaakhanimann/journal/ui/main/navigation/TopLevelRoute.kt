package com.isaakhanimann.journal.ui.main.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.isaakhanimann.journal.R
import kotlinx.serialization.Serializable

import com.isaakhanimann.journal.ui.main.navigation.graphs.DrugsScreenRoute
import com.isaakhanimann.journal.ui.main.navigation.graphs.JournalScreenRoute
import com.isaakhanimann.journal.ui.main.navigation.graphs.SaferUseScreenRoute
import com.isaakhanimann.journal.ui.main.navigation.graphs.SettingsScreenRoute
import com.isaakhanimann.journal.ui.main.navigation.graphs.StatsScreenRoute
import kotlin.reflect.KClass

data class TopLevelRoute<T : Any>(
    @StringRes val nameRes: Int,
    val route: T,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val startRoute: KClass<*>
)

val topLevelRoutes = listOf(
    TopLevelRoute(R.string.nav_stats, StatsTopLevelRoute, Icons.Filled.BarChart, Icons.Outlined.BarChart, StatsScreenRoute::class),
    TopLevelRoute(R.string.nav_journal, JournalTopLevelRoute, Icons.Filled.Book, Icons.Outlined.Book, JournalScreenRoute::class),
    TopLevelRoute(R.string.nav_drugs, DrugsTopLevelRoute, Icons.Filled.Medication, Icons.Outlined.Medication, DrugsScreenRoute::class),
    TopLevelRoute(R.string.nav_safer, SaferUseTopLevelRoute, Icons.Filled.HealthAndSafety, Icons.Outlined.HealthAndSafety, SaferUseScreenRoute::class),
    TopLevelRoute(R.string.nav_settings, SettingsTopLevelRoute, Icons.Filled.Settings, Icons.Outlined.Settings, SettingsScreenRoute::class)
)

@Serializable
object StatsTopLevelRoute

@Serializable
object JournalTopLevelRoute

@Serializable
object DrugsTopLevelRoute

@Serializable
object SaferUseTopLevelRoute

@Serializable
object SettingsTopLevelRoute