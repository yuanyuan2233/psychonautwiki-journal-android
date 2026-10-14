/*
 * Copyright (c) 2023. Isaak Hanimann.
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

package com.isaakhanimann.journal.ui.tabs.journal.experience.components

import androidx.annotation.StringRes
import com.isaakhanimann.journal.R

enum class SavedTimeDisplayOption(@StringRes val titleRes: Int) {
    AUTO(R.string.journal_time_display_auto) {
        override val text = "Auto"
    }, RELATIVE_TO_NOW(R.string.journal_time_display_relative_to_now) {
        override val text = "Time relative to now"
    }, RELATIVE_TO_START(R.string.journal_time_display_relative_to_start) {
        override val text = "Time relative to start"
    }, TIME_BETWEEN(R.string.journal_time_display_time_between) {
        override val text = "Time between"
    }, REGULAR(R.string.journal_time_display_regular) {
        override val text = "Regular time"
    };

    abstract val text: String
}


enum class TimeDisplayOption {
    RELATIVE_TO_NOW, RELATIVE_TO_START, TIME_BETWEEN, REGULAR;
}