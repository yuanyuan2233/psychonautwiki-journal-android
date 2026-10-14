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

package com.isaakhanimann.journal.ui.tabs.journal.experience.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.ui.utils.getShortTimeText
import com.isaakhanimann.journal.ui.utils.getShortTimeWithWeekdayText
import com.isaakhanimann.journal.ui.utils.getShortWeekdayText
import java.time.Instant

const val TIME_RANGE_SEPARATOR_TEXT = " - "

@Composable
fun IngestionTimeOrDurationText(
    time: Instant,
    endTime: Instant?,
    index: Int,
    timeDisplayOption: TimeDisplayOption,
    allTimesSortedMap: List<Instant>
) {
    val isFirstIngestion = index == 0
    val textStyle = MaterialTheme.typography.labelMedium
    when (timeDisplayOption) {
        TimeDisplayOption.REGULAR -> {
            RegularTimeOrRangeText(time, endTime, textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_NOW -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TimeRelativeToNowText(
                    time = time,
                    style = textStyle
                )
                if (endTime != null) {
                    Text(TIME_RANGE_SEPARATOR_TEXT)
                    TimeRelativeToNowText(
                        time = endTime,
                        style = textStyle
                    )
                }
            }
        }

        TimeDisplayOption.TIME_BETWEEN -> {
            if (isFirstIngestion) {
                RegularTimeOrRangeText(time, endTime, textStyle)
            } else {
                val previousTime =
                    allTimesSortedMap[index - 1]
                val context = LocalContext.current
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (endTime != null) {
                        Text(
                            text = getDurationText(
                                fromInstant = previousTime,
                                toInstant = time,
                                context = context
                            ),
                            style = textStyle
                        )
                        Text(TIME_RANGE_SEPARATOR_TEXT)
                        val endDuration = getDurationText(
                            fromInstant = previousTime,
                            toInstant = endTime,
                            context = context
                        )
                        Text(
                            text = stringResource(R.string.journal_time_after_previous, endDuration),
                            style = textStyle
                        )
                    } else {
                        val duration = getDurationText(
                            fromInstant = previousTime,
                            toInstant = time,
                            context = context
                        )
                        Text(
                            text = stringResource(R.string.journal_time_after_previous, duration),
                            style = textStyle
                        )
                    }
                }
            }
        }

        TimeDisplayOption.RELATIVE_TO_START -> {
            if (isFirstIngestion) {
                RegularTimeOrRangeText(time, endTime, textStyle)
            } else {
                val context = LocalContext.current
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val startTime = allTimesSortedMap.firstOrNull()
                        ?: Instant.now()
                    if (endTime != null) {
                        Text(
                            text = getDurationText(
                                fromInstant = startTime,
                                toInstant = time,
                                context = context
                            ),
                            style = textStyle
                        )
                        Text(TIME_RANGE_SEPARATOR_TEXT)
                        val endDuration = getDurationText(
                            fromInstant = startTime,
                            toInstant = endTime,
                            context = context
                        )
                        Text(
                            text = stringResource(R.string.journal_time_after_start, endDuration),
                            style = textStyle
                        )
                    } else {
                        val duration = getDurationText(
                            fromInstant = startTime,
                            toInstant = time,
                            context = context
                        )
                        Text(
                            text = stringResource(R.string.journal_time_after_start, duration),
                            style = textStyle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RegularTimeOrRangeText(
    time: Instant,
    endTime: Instant?,
    textStyle: TextStyle
) {
    val startText = time.getShortTimeWithWeekdayText()
    val text = if (endTime == null) {
        startText
    } else {
        val startDay = time.getShortWeekdayText()
        val endDay = endTime.getShortWeekdayText()
        if (startDay == endDay) {
            startText + TIME_RANGE_SEPARATOR_TEXT + endTime.getShortTimeText()
        } else {
            startText + TIME_RANGE_SEPARATOR_TEXT + endTime.getShortTimeWithWeekdayText()
        }
    }
    Text(
        text = text,
        style = textStyle
    )
}

@Composable
fun NoteOrRatingTimeOrDurationText(
    time: Instant,
    timeDisplayOption: TimeDisplayOption,
    firstIngestionTime: Instant
) {
    val textStyle = MaterialTheme.typography.labelMedium
    when (timeDisplayOption) {
        TimeDisplayOption.REGULAR -> {
            val startText = time.getShortTimeWithWeekdayText()
            Text(text = startText, style = textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_NOW -> {
            TimeRelativeToNowText(
                time = time,
                style = textStyle
            )
        }

        TimeDisplayOption.TIME_BETWEEN -> {
            val startText = time.getShortTimeWithWeekdayText()
            Text(text = startText, style = textStyle)
        }

        TimeDisplayOption.RELATIVE_TO_START -> {
            val context = LocalContext.current
            val durationText = getDurationText(
                fromInstant = firstIngestionTime,
                toInstant = time,
                context = context
            )
            val text = if (firstIngestionTime < time) {
                stringResource(R.string.journal_time_after_start, durationText)
            } else {
                stringResource(R.string.journal_time_before_start, durationText)
            }
            Text(
                text = text,
                style = textStyle
            )
        }
    }
}