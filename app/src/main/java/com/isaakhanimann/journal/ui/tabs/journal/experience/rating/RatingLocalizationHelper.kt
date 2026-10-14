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

package com.isaakhanimann.journal.ui.tabs.journal.experience.rating

import androidx.annotation.StringRes
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.room.experiences.entities.ShulginRatingOption

@StringRes
fun ShulginRatingOption.getLocalizedShortDescriptionRes(): Int {
    return when (this) {
        ShulginRatingOption.MINUS -> R.string.journal_shulgin_minus_short
        ShulginRatingOption.PLUS_MINUS -> R.string.journal_shulgin_plus_minus_short
        ShulginRatingOption.PLUS -> R.string.journal_shulgin_plus_short
        ShulginRatingOption.TWO_PLUS -> R.string.journal_shulgin_two_plus_short
        ShulginRatingOption.THREE_PLUS -> R.string.journal_shulgin_three_plus_short
        ShulginRatingOption.FOUR_PLUS -> R.string.journal_shulgin_four_plus_short
    }
}

@StringRes
fun ShulginRatingOption.getLocalizedLongDescriptionRes(): Int {
    return when (this) {
        ShulginRatingOption.MINUS -> R.string.journal_shulgin_minus_long
        ShulginRatingOption.PLUS_MINUS -> R.string.journal_shulgin_plus_minus_long
        ShulginRatingOption.PLUS -> R.string.journal_shulgin_plus_long
        ShulginRatingOption.TWO_PLUS -> R.string.journal_shulgin_two_plus_long
        ShulginRatingOption.THREE_PLUS -> R.string.journal_shulgin_three_plus_long
        ShulginRatingOption.FOUR_PLUS -> R.string.journal_shulgin_four_plus_long
    }
}
