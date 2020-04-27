package io.github.hnosmium0001.deep_mining.library.multiblock

import io.github.hnosmium0001.deep_mining.util.plus
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i

class MultiBlockType(
    val offsets: Array<Vec3i>,
    val matchers: Array<BlockMatcher>
) {
    companion object {
        fun fromPairs(vararg zipped: Pair<Vec3i, BlockMatcher>): MultiBlockType {
            val unzipped = zipped.unzip()
            return MultiBlockType(unzipped.first.toTypedArray(), unzipped.second.toTypedArray())
        }
    }
}

open class MultiBlockController(
    props: TileEntityType<*>,
    val type: MultiBlockType
) : TileEntity(props) {
    val membersSeq: Sequence<BlockPos>
        get() = type.offsets
            .asSequence()
            .map { offset -> pos + offset }
    val members: Set<BlockPos> by lazy { membersSeq.toCollection(HashSet()) }

    fun memberTiles() = members
        .asSequence()
        .map { world!!.getTileEntity(it) }

    fun isComplete(): Boolean {
        for ((member, matcher) in members.zip(type.matchers)) {
            val state = world!!.getBlockState(member)
            if (!matcher.matches(state)) {
                return false
            }
        }
        return true
    }
}