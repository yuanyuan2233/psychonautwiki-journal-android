package com.isaakhanimann.journal.ui.utils

import androidx.annotation.StringRes
import com.isaakhanimann.journal.R
import com.isaakhanimann.journal.data.room.experiences.entities.ShulginRatingOption
import com.isaakhanimann.journal.data.room.experiences.entities.StomachFullness
import com.isaakhanimann.journal.data.substances.AdministrationRoute

/**
 * 本地化枚举扩展：通过扩展属性将领域模型枚举与 Android 字符串资源解耦映射。
 * 绝不修改枚举类自身的 name、rawValue、serialized 等持久化标识，保证 SQLite 数据库与 JSON 备份完全无损兼容。
 */

val AdministrationRoute.titleRes: Int
    @StringRes
    get() = when (this) {
        AdministrationRoute.ORAL -> R.string.route_oral
        AdministrationRoute.SUBLINGUAL -> R.string.route_sublingual
        AdministrationRoute.BUCCAL -> R.string.route_buccal
        AdministrationRoute.INSUFFLATED -> R.string.route_insufflated
        AdministrationRoute.RECTAL -> R.string.route_rectal
        AdministrationRoute.TRANSDERMAL -> R.string.route_transdermal
        AdministrationRoute.SUBCUTANEOUS -> R.string.route_subcutaneous
        AdministrationRoute.INTRAMUSCULAR -> R.string.route_intramuscular
        AdministrationRoute.INTRAVENOUS -> R.string.route_intravenous
        AdministrationRoute.SMOKED -> R.string.route_smoked
        AdministrationRoute.INHALED -> R.string.route_inhaled
    }

val AdministrationRoute.descriptionRes: Int
    @StringRes
    get() = when (this) {
        AdministrationRoute.ORAL -> R.string.route_oral_desc
        AdministrationRoute.SUBLINGUAL -> R.string.route_sublingual_desc
        AdministrationRoute.BUCCAL -> R.string.route_buccal_desc
        AdministrationRoute.INSUFFLATED -> R.string.route_insufflated_desc
        AdministrationRoute.RECTAL -> R.string.route_rectal_desc
        AdministrationRoute.TRANSDERMAL -> R.string.route_transdermal_desc
        AdministrationRoute.SUBCUTANEOUS -> R.string.route_subcutaneous_desc
        AdministrationRoute.INTRAMUSCULAR -> R.string.route_intramuscular_desc
        AdministrationRoute.INTRAVENOUS -> R.string.route_intravenous_desc
        AdministrationRoute.SMOKED -> R.string.route_smoked_desc
        AdministrationRoute.INHALED -> R.string.route_inhaled_desc
    }

val StomachFullness.titleRes: Int
    @StringRes
    get() = when (this) {
        StomachFullness.EMPTY -> R.string.stomach_empty
        StomachFullness.QUARTER_FULL -> R.string.stomach_quarter_full
        StomachFullness.HALF_FULL -> R.string.stomach_half_full
        StomachFullness.FULL -> R.string.stomach_full
        StomachFullness.VERY_FULL -> R.string.stomach_very_full
    }

val ShulginRatingOption.shortDescriptionRes: Int
    @StringRes
    get() = when (this) {
        ShulginRatingOption.MINUS -> R.string.shulgin_minus
        ShulginRatingOption.PLUS_MINUS -> R.string.shulgin_plus_minus
        ShulginRatingOption.PLUS -> R.string.shulgin_plus
        ShulginRatingOption.TWO_PLUS -> R.string.shulgin_two_plus
        ShulginRatingOption.THREE_PLUS -> R.string.shulgin_three_plus
        ShulginRatingOption.FOUR_PLUS -> R.string.shulgin_four_plus
    }
