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

package dev.nathanpb.dml.utils

import dev.nathanpb.dml.accessor.ITrialWorldPersistenceAccessor
import dev.nathanpb.dml.trial.Trial
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World
import java.util.*

fun World.getEntitiesAroundCircle(type: EntityType<*>?, pos: BlockPos, radius: Double) : List<Entity> {
    val squaredRadius = radius * radius
    val pos3d = pos.toVec3d()
    return this.getEntitiesByType(type, Box(
        pos.x - radius,
        pos.y - 1.0,
        pos.z - radius,
        pos.x + radius,
        pos.y + radius,
        pos.z + radius
    )) {
        it.squaredDistanceTo(pos3d) <= squaredRadius
    }.orEmpty()
}

val World.runningTrials: MutableList<Trial>
    get() = (this as ITrialWorldPersistenceAccessor).runningTrials

fun World.getPlayersByUUID(uuids: HashSet<UUID>): List<PlayerEntity> {
    return players.filter { it.uuid in uuids }
}
