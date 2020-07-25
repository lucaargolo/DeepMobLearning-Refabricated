/*
 * Copyright (C) 2020 Nathan P. Bombana, IterationFunk
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.
 */

package dev.nathanpb.dml.data

import dev.nathanpb.dml.config
import net.minecraft.text.TranslatableText
import kotlin.math.ceil
import kotlin.math.max

// TODO remove the hardcoded dataAmount
enum class DataModelTier(textEntry: String, private val dataAmountSupplier: ()->Int) {
    FAULTY("tier.deepmoblearning.faulty", { 0 }),
    BASIC("tier.deepmoblearning.basic", config.dataModel::basicDataRequired),
    ADVANCED("tier.deepmoblearning.advanced", config.dataModel::advancedDataRequired),
    SUPERIOR("tier.deepmoblearning.superior", config.dataModel::superiorDataRequired),
    SELF_AWARE("tier.deepmoblearning.self_aware", config.dataModel::selfAwareDataRequired);

    val dataAmount: Int
        get() = dataAmountSupplier()

    companion object {
        fun fromDataAmount(amount: Int) = values().last {
            it.dataAmount <= max(amount, 0)
        }

        fun fromIndex(index: Int): DataModelTier? {
            return if (index in (0.until(values().size))) {
                values()[index]
            } else null
        }
    }

    val text = TranslatableText(textEntry)
    fun isMaxTier() = this == values().last()
    fun nextTierOrCurrent() = if (isMaxTier()) SELF_AWARE else values()[ordinal+1]
    val defaultWaveEntityCount = ceil((ordinal + 1) * 1.5).toInt()
    val systemGlitchMaxHealth = (ordinal + 1) * 100F
    val defaultWaveRespawnTimeout by lazy {
        (values().size - ordinal) * 5 * 20
    }
}
