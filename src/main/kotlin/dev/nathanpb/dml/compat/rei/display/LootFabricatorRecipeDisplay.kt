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

package dev.nathanpb.dml.compat.rei.display

import dev.nathanpb.dml.recipe.LootFabricatorRecipe
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.minecraft.util.Identifier

class LootFabricatorRecipeDisplay (
    private val categoryId: Identifier,
    val recipe: LootFabricatorRecipe
) : RecipeDisplay {

    private val input = mutableListOf(recipe.input.matchingStacksClient.map(EntryStack::create).toMutableList())

    override fun getRecipeCategory() = categoryId

    override fun getInputEntries() = input

    override fun getOutputEntries() = mutableListOf<EntryStack>()

}
