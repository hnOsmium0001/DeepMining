package io.github.hnosmium0001.deep_mining.drill

import io.github.hnosmium0001.deep_mining.CommonConfig
import io.github.hnosmium0001.deep_mining.library.multiblock.*
import io.github.hnosmium0001.deep_mining.mineralDrillTileEntity
import io.github.hnosmium0001.deep_mining.util.unwrap
import io.github.hnosmium0001.deep_mining.vein.CAPABILITY_MINERAL_VEINS
import io.github.hnosmium0001.deep_mining.vein.MineralVein
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Vec3i
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class MineralDrillBlock(props: Properties) : Block(props) {
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING)
    }
}

// TODO
val mineralDrillMultiblock = MultiBlockType.fromPairs(
    Vec3i(0, 0, 0) to ListedBlockMatcher(Blocks.IRON_BLOCK.defaultState)
)

class MineralDrillTileEntity :
    MultiBlockController(mineralDrillTileEntity.unwrap()!!, mineralDrillMultiblock),
    ITickableTileEntity {

    private var needsReload = true

    private var targetVein: MineralVein? = null
    private var density: Double = 0.0
    private var bonusProgress = 0.0f
    private val storage = LazyOptional.of { ItemStackHandler() }

    val modules = Modules()

    override fun tick() {
        if (needsReload) {
            val veins = world!!.getCapability(CAPABILITY_MINERAL_VEINS).unwrap()
            val chunk = ChunkPos(this.pos)
            targetVein = veins?.chunks?.get(chunk)?.asSequence()
                    ?.filter { it.densityAt(this.pos) > 0.0 }
                    ?.first()
            density = targetVein?.densityAt(this.pos) ?: 0.0

            needsReload = false
        }

        work()
    }

    private fun work() {
        if (targetVein == null) return
        val targetVein = this.targetVein!!
        val storage = this.storage.unwrap()!!
        val stack = storage.getStackInSlot(0)
        val spaceAvailable = stack.let { it.maxStackSize - it.count }

        val bonus = if (bonusProgress >= 1.0f) 1 else 0
        val bonusUse = bonus.coerceAtMost(spaceAvailable)
        // Attempt to use bonus output first
        val prodUse = CommonConfig.mineralDrillPerformance.get().coerceAtMost(spaceAvailable - bonusUse)

        // Do modification
        val output = bonusUse + prodUse
        if (output > 0) {
            storage.insertItem(0, stack.copy().apply { count = output }, false)
        }
        targetVein.remaining -= prodUse
        bonusProgress += if (prodUse > 0) 0.01f * modules.productivity else 0.0f

        if (targetVein.isDepleted) this.targetVein = null
    }

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> storage.cast()
            else -> super.getCapability(cap, side)
        }
    }
}