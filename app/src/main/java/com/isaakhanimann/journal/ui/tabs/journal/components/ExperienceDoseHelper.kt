/*
 * Copyright (c) 2022-2026. Isaak Hanimann & Contributors.
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

package com.isaakhanimann.journal.ui.tabs.journal.components

import com.isaakhanimann.journal.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import com.isaakhanimann.journal.ui.tabs.search.substance.roa.toReadableString

object ExperienceDoseHelper {

    /**
     * 计算并格式化一个 Experience 下所有 ingestions 的总剂量文本。
     * 针对用户在药效内的多次追加（同种药物），将剂量进行数学累加，并处理估算与单位。
     */
    fun formatExperienceDose(
        ingestions: List<IngestionWithCompanionAndCustomUnit>
    ): String? {
        if (ingestions.isEmpty()) return null

        // 优先筛选本人（consumerName == null）的用药记录，如果全部都是同服者的则考虑全部
        val myIngestions = ingestions.filter { it.ingestion.consumerName == null }.ifEmpty { ingestions }

        // 按物质名称分组
        val bySubstance = myIngestions.groupBy { it.ingestion.substanceName }

        val substanceDoseSummaries = bySubstance.mapNotNull { (_, substanceIngestions) ->
            formatSubstanceCumulativeDose(substanceIngestions)
        }

        if (substanceDoseSummaries.isEmpty()) return null

        // 针对单物质与多物质的处理
        return if (substanceDoseSummaries.size <= 2) {
            substanceDoseSummaries.joinToString(separator = " + ")
        } else {
            // 超过 2 种物质时，取前 2 种并附注更多
            "${substanceDoseSummaries.take(2).joinToString(separator = " + ")}..."
        }
    }

    /**
     * 针对同一种物质，将其多次服药（单次或多次追加）统计累加
     */
    fun formatSubstanceCumulativeDose(
        substanceIngestions: List<IngestionWithCompanionAndCustomUnit>
    ): String? {
        // 过滤出有有效剂量的条目
        val validDoseIngestions = substanceIngestions.filter { it.pureDose != null && it.pureDose!! > 0 }
        if (validDoseIngestions.isEmpty()) return null

        // 获取主要单位（以第一个有效单位为准）
        val primaryUnit = validDoseIngestions.mapNotNull { it.originalUnit }.firstOrNull() ?: ""

        // 检查单位一致性：若单位一致，进行累加
        val sameUnitIngestions = validDoseIngestions.filter { (it.originalUnit ?: "") == primaryUnit }
        if (sameUnitIngestions.isEmpty()) return null

        val totalPureDose = sameUnitIngestions.sumOf { it.pureDose ?: 0.0 }
        if (totalPureDose <= 0) return null

        // 是否为估算剂量：
        // 1. 任一单次标记了 isEstimate
        // 2. 总条目数大于已统计条目数（说明有的追加没填剂量，只能是估算下限）
        val anyIsEstimate = sameUnitIngestions.any { it.isEstimate } || substanceIngestions.size > sameUnitIngestions.size

        // 累加标准差（如果有）
        val totalDeviation = sameUnitIngestions.mapNotNull { it.pureDoseStandardDeviation }.sum()

        val doseNumberStr = totalPureDose.toReadableString()

        val doseWithDeviationOrEstimate = when {
            totalDeviation > 0 -> {
                "$doseNumberStr±${totalDeviation.toReadableString()}"
            }
            anyIsEstimate -> {
                "~$doseNumberStr"
            }
            else -> {
                doseNumberStr
            }
        }

        return if (primaryUnit.isNotBlank()) {
            "$doseWithDeviationOrEstimate $primaryUnit"
        } else {
            doseWithDeviationOrEstimate
        }
    }
}
