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

package dev.nathanpb.dml.blockEntity

import dev.nathanpb.dml.config
import dev.nathanpb.dml.data.ModularArmorData
import dev.nathanpb.dml.inventory.MatterCondenserInventory
import dev.nathanpb.dml.item.ItemModularGlitchArmor
import dev.nathanpb.dml.item.ItemPristineMatter
import dev.nathanpb.dml.utils.items
import dev.nathanpb.dml.utils.setStacks
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.minecraft.block.BlockState
import net.minecraft.block.InventoryProvider
import net.minecraft.block.entity.BlockEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.util.Tickable
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

class BlockEntityMatterCondenser : BlockEntity(BLOCKENTITY_MATTER_CONDENSER),
    InventoryProvider,
    PropertyDelegateHolder,
    Tickable
{

    val inventory = MatterCondenserInventory()
    private val propertyDelegate = ArrayPropertyDelegate(2).also {
        it[1] = config.matterCondenser.processTime
    }

    private var progress by Delegates.observable(0) { _, _, value ->
        propertyDelegate[0] = value
    }

    override fun tick() {
        if (world?.isClient == false) {
            val armorStack = inventory.stackInArmorSlot
            if (armorStack.item is ItemModularGlitchArmor) {
                val matterStacks = inventory.matterStacks.filterNot(ItemStack::isEmpty)
                if (matterStacks.isNotEmpty() && matterStacks.all { it.item is ItemPristineMatter }) {
                    val data = ModularArmorData(armorStack)
                    if (!data.tier().isMaxTier()) {
                        val processTime = config.matterCondenser.processTime
                        propertyDelegate[1] = processTime

                        progress++
                        if (progress >= processTime) {
                            val pristineMattersAvailable = inventory.matterStacks.filter {
                                it.item is ItemPristineMatter
                            }

                            val toConsume = min(max(0, data.dataRemainingToNextTier()), pristineMattersAvailable.size)
                            pristineMattersAvailable.take(toConsume).forEach {
                                it.decrement(1)
                            }
                            data.dataAmount += toConsume
                            resetProgress()
                        }
                        return
                    }
                }
            }
            resetProgress()
        }
    }

    private fun resetProgress() {
        if (progress != 0) {
            progress = 0
        }
    }

    override fun getInventory(state: BlockState?, world: WorldAccess?, pos: BlockPos?) = inventory

    override fun getPropertyDelegate() = propertyDelegate

    override fun toTag(tag: CompoundTag?): CompoundTag {
        return super.toTag(tag).also {
            if (tag != null) {
                Inventories.toTag(tag, inventory.items())
            }
        }
    }

    override fun fromTag(state: BlockState?, tag: CompoundTag?) {
        super.fromTag(state, tag)
        if (tag != null) {
            val list = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY)
            Inventories.fromTag(tag, list)
            inventory.setStacks(list)
        }
    }
}
