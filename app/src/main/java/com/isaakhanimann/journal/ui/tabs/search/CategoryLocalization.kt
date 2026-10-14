/*
 * Copyright (c) 2024. Isaak Hanimann.
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

package com.isaakhanimann.journal.ui.tabs.search

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.isaakhanimann.journal.R

@StringRes
fun getCategoryStringRes(categoryName: String): Int? {
    return when (categoryName.lowercase()) {
        "common" -> R.string.drugs_category_common
        "psychedelic" -> R.string.drugs_category_psychedelic
        "stimulant" -> R.string.drugs_category_stimulant
        "entactogen" -> R.string.drugs_category_entactogen
        "depressant" -> R.string.drugs_category_depressant
        "opioid" -> R.string.drugs_category_opioid
        "habit-forming" -> R.string.drugs_category_habit_forming
        "research-chemical" -> R.string.drugs_category_research_chemical
        "tentative" -> R.string.drugs_category_tentative
        "dissociative" -> R.string.drugs_category_dissociative
        "benzodiazepine" -> R.string.drugs_category_benzodiazepine
        "cannabinoid" -> R.string.drugs_category_cannabinoid
        "nootropic" -> R.string.drugs_category_nootropic
        "deliriant" -> R.string.drugs_category_deliriant
        "barbiturate" -> R.string.drugs_category_barbiturate
        "eugeroic" -> R.string.drugs_category_eugeroic
        "hallucinogen" -> R.string.drugs_category_hallucinogen
        "oneirogen" -> R.string.drugs_category_oneirogen
        "antipsychotic" -> R.string.drugs_category_antipsychotic
        "antidepressant" -> R.string.drugs_category_antidepressant
        "hypnotic" -> R.string.drugs_category_hypnotic
        "custom" -> R.string.drugs_category_custom
        else -> null
    }
}

@Composable
fun getCategoryDisplayName(categoryName: String): String {
    val resId = getCategoryStringRes(categoryName)
    return if (resId != null) {
        stringResource(resId)
    } else {
        categoryName
    }
}
