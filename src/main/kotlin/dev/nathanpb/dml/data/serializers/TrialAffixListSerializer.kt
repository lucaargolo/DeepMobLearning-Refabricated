/*
 * Copyright (C) 2020 Nathan P. Bombana, IterationFunk
 *
 * This file is part of Deep Mob Learning: Refabricated.
 *
 * Deep Mob Learning: Refabricated is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Deep Mob Learning: Refabricated is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Deep Mob Learning: Refabricated.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.nathanpb.dml.data.serializers

import dev.nathanpb.dml.trial.affix.core.TrialAffix
import dev.nathanpb.dml.trial.affix.core.TrialAffixRegistry
import dev.nathanpb.ktdatatag.serializer.AbstractListTagSerializer
import net.minecraft.nbt.StringTag
import net.minecraft.util.Identifier

class TrialAffixListSerializer : AbstractListTagSerializer<TrialAffix, StringTag>(
    StringTag.of("").type,
    StringTag::class.java,
    {
        val id = Identifier(it.asString())
        TrialAffixRegistry.INSTANCE.findById(id)
            ?: TrialAffixRegistry.INSTANCE.all.first() // Trying to recover the error, may throw NPE anyway
    },
    { StringTag.of(it.id.toString()) }
)
