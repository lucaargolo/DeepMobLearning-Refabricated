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

import dev.nathanpb.dml.accessor.IFlightBurnoutManagerAccessor
import dev.nathanpb.dml.accessor.ILivingEntityReiStateAccessor
import dev.nathanpb.dml.accessor.ITrialWorldPersistenceAccessor
import dev.nathanpb.dml.accessor.IUndyingCooldown
import dev.nathanpb.dml.armor.modular.cooldown.FlightBurnoutManager
import dev.nathanpb.dml.trial.Trial
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

var LivingEntity.isInReiScreen: Boolean
    get() = (this as ILivingEntityReiStateAccessor).isDmlRefIsInReiScreen
    set(flag) {
        (this as ILivingEntityReiStateAccessor).setDmlRefInReiScreen(flag)
    }

val World.runningTrials: MutableList<Trial>
    get() = (this as ITrialWorldPersistenceAccessor).runningTrials

val PlayerEntity.flightBurnoutManager: FlightBurnoutManager
    get() = (this as IFlightBurnoutManagerAccessor).dmlFlightBurnoutManager

var PlayerEntity.undyingLastUsage
    get() = (this as IUndyingCooldown).dmlRefUndyingLastUsage
    set(value) {
        (this as IUndyingCooldown).dmlRefUndyingLastUsage = value
    }
