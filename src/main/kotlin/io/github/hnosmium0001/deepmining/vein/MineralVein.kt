package io.github.hnosmium0001.deepmining.vein

import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import kotlin.math.sqrt

class MineralVein(val center: BlockPos, val density: Float, val radius: Int, val composition: MineralComposition) {

    fun densityAt(pos: BlockPos): Double {
        val xd = center.x - pos.x
        val yd = center.y - pos.y
        val zd = center.z - pos.z
        val dist = sqrt((xd * xd + yd * yd + zd * zd).toDouble())
        return this.fade(dist)
    }

    fun fade(dist: Double): Double {
        // TODO better distance function
        @Suppress("NAME_SHADOWING")
        val dist = dist.coerceIn(0.5, radius.toDouble())
        return (density * 256) / (dist * dist)
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
}

data class Mineral(val item: ItemStack, val weight: Float)