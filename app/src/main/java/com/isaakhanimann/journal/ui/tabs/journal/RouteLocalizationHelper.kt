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

package com.isaakhanimann.journal.ui.tabs.journal

import androidx.annotation.StringRes
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.substances.AdministrationRoute

@StringRes
fun AdministrationRoute.getLocalizedNameRes(): Int {
    return when (this) {
        AdministrationRoute.ORAL -> R.string.journal_route_oral
        AdministrationRoute.SUBLINGUAL -> R.string.journal_route_sublingual
        AdministrationRoute.BUCCAL -> R.string.journal_route_buccal
        AdministrationRoute.INSUFFLATED -> R.string.journal_route_insufflated
        AdministrationRoute.RECTAL -> R.string.journal_route_rectal
        AdministrationRoute.TRANSDERMAL -> R.string.journal_route_transdermal
        AdministrationRoute.SUBCUTANEOUS -> R.string.journal_route_subcutaneous
        AdministrationRoute.INTRAMUSCULAR -> R.string.journal_route_intramuscular
        AdministrationRoute.INTRAVENOUS -> R.string.journal_route_intravenous
        AdministrationRoute.SMOKED -> R.string.journal_route_smoked
        AdministrationRoute.INHALED -> R.string.journal_route_inhaled
    }
}

@StringRes
fun AdministrationRoute.getLocalizedDescRes(): Int {
    return when (this) {
        AdministrationRoute.ORAL -> R.string.journal_route_oral_desc
        AdministrationRoute.SUBLINGUAL -> R.string.journal_route_sublingual_desc
        AdministrationRoute.BUCCAL -> R.string.journal_route_buccal_desc
        AdministrationRoute.INSUFFLATED -> R.string.journal_route_insufflated_desc
        AdministrationRoute.RECTAL -> R.string.journal_route_rectal_desc
        AdministrationRoute.TRANSDERMAL -> R.string.journal_route_transdermal_desc
        AdministrationRoute.SUBCUTANEOUS -> R.string.journal_route_subcutaneous_desc
        AdministrationRoute.INTRAMUSCULAR -> R.string.journal_route_intramuscular_desc
        AdministrationRoute.INTRAVENOUS -> R.string.journal_route_intravenous_desc
        AdministrationRoute.SMOKED -> R.string.journal_route_smoked_desc
        AdministrationRoute.INHALED -> R.string.journal_route_inhaled_desc
    }
}
