@file:Suppress("NAME_SHADOWING")

package io.github.hnosmium0001.deep_mining.vein

import io.github.hnosmium0001.deep_mining.util.pack
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.NBTUtil
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.util.Constants
import kotlin.math.sqrt

class MineralVein(
        val center: BlockPos,
        val density: Float,
        val radius: Int,
        val composition: MineralComposition,
        var remaining: Int
) {
    val isDepleted get() = remaining >= 0

    fun densityAt(pos: BlockPos): Double {
        val xd = center.x - pos.x
        val yd = center.y - pos.y
        val zd = center.z - pos.z
        val dist = sqrt((xd * xd + yd * yd + zd * zd).toDouble())
        return this.fade(dist)
    }

    fun fade(dist: Double): Double {
        // TODO better distance function
        val dist = dist.coerceIn(0.5, radius.toDouble())
        return (density * 256) / (dist * dist)
    }

    fun write(tag: CompoundNBT = CompoundNBT()): CompoundNBT {
        tag.put("Center", NBTUtil.writeBlockPos(center))
        tag.putFloat("Density", density)
        tag.putInt("Radius", radius)
        tag.put("Composition", composition.write())
        return tag
    }

    companion object {
        fun read(tag: CompoundNBT): MineralVein {
            val center = NBTUtil.readBlockPos(tag.getCompound("Center"))
            val density = tag.getFloat("Density")
            val radius = tag.getInt("Radius")
            val composition = MineralComposition.read(tag.getCompound("Composition"))
            val remaining = tag.getInt("Remaining")
            return MineralVein(center, density, radius, composition, remaining)
        }
    }
}

data class MineralComposition(val minerals: Array<Mineral>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MineralComposition

        if (!minerals.contentEquals(other.minerals)) return false

        return true
    }

    override fun hashCode(): Int {
        return minerals.contentHashCode()
    }

    fun write(tag: CompoundNBT = CompoundNBT()): CompoundNBT {
        tag.put("Minerals", pack(minerals) { m -> m.write() })
        return tag
    }

    companion object {
        fun read(tag: CompoundNBT): MineralComposition {
            val nbtMinerals = tag.getList("Minerals", Constants.NBT.TAG_COMPOUND)
            val minerals = Array(nbtMinerals.size) {
                val nbtMineral = nbtMinerals.getCompound(it)
                val item = ItemStack.read(nbtMineral.getCompound("Item"))
                val weight = tag.getFloat("Weight")
                Mineral(item, weight)
            }
            return MineralComposition(minerals)
        }
    }
}

data class Mineral(val item: ItemStack, val weight: Float) {
    fun write(tag: CompoundNBT = CompoundNBT()): CompoundNBT {
        tag.put("Item", item.write(CompoundNBT()))
        tag.putFloat("Weight", weight)
        return tag
    }
}