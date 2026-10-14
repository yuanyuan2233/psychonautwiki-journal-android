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

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.tabs.journal.components.roundToOneDecimal
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.absoluteValue

fun getDurationText(fromInstant: Instant, toInstant: Instant, context: Context? = null): String {
    val duration = Duration.between(fromInstant, toInstant)
    val isZh = context?.resources?.configuration?.locales?.get(0)?.language == "zh" ||
            Locale.getDefault().language == "zh"
    return when {
        duration.toDays().absoluteValue > 500 -> {
            val years = roundToOneDecimal(duration.toDays().toDouble().absoluteValue / 365.0)
            if (context != null) context.getString(R.string.journal_duration_years, years)
            else if (isZh) "$years 年" else "$years years"
        }

        duration.toDays().absoluteValue > 60 -> {
            val months = roundToOneDecimal(duration.toDays().toDouble().absoluteValue / 30.0)
            if (context != null) context.getString(R.string.journal_duration_months, months)
            else if (isZh) "$months 个月" else "$months months"
        }

        duration.toDays().absoluteValue > 20 -> {
            val weeks = roundToOneDecimal(duration.toDays().toDouble().absoluteValue / 7.0)
            if (context != null) context.getString(R.string.journal_duration_weeks, weeks)
            else if (isZh) "$weeks 周" else "$weeks weeks"
        }

        duration.toDays().absoluteValue > 4 -> {
            val days = duration.toDays().absoluteValue
            if (context != null) context.getString(R.string.journal_duration_days, days.toString())
            else if (isZh) "$days 天" else "$days days"
        }

        duration.toHours().absoluteValue > 40 -> {
            val days = roundToOneDecimal(duration.toHours().toDouble().absoluteValue / 24.0)
            if (context != null) context.getString(R.string.journal_duration_days, days)
            else if (isZh) "$days 天" else "$days days"
        }

        duration.toHours().absoluteValue > 1 -> {
            val hours = roundToOneDecimal(duration.toMinutes().toDouble().absoluteValue / 60.0)
            if (context != null) context.getString(R.string.journal_duration_hours, hours)
            else if (isZh) "$hours 小时" else "$hours hr"
        }

        duration.toMinutes().absoluteValue > 0 -> {
            val minutes = duration.toMinutes().absoluteValue
            if (context != null) context.getString(R.string.journal_duration_minutes, minutes.toString())
            else if (isZh) "$minutes 分钟" else "$minutes min"
        }

        else -> {
            val seconds = (duration.toMillis().toDouble().absoluteValue / 1000.0).toInt()
            if (context != null) context.getString(R.string.journal_duration_seconds, seconds)
            else if (isZh) "$seconds 秒" else "$seconds s"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeRelativeToStartTextPreview() {
    Column {

        Text(
            text = getDurationText(
                fromInstant = Instant.now().minus(4, ChronoUnit.HOURS),
                toInstant = Instant.now()
            )
        )
        Text(
            text = getDurationText(
                fromInstant = Instant.now().minus(20, ChronoUnit.MINUTES),
                toInstant = Instant.now()
            )
        )
    }
}