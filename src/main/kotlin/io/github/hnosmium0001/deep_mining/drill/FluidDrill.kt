package io.github.hnosmium0001.deep_mining.drill

import io.github.hnosmium0001.deep_mining.fluidDrillTileEntity
import io.github.hnosmium0001.deep_mining.util.unwrap
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.tileentity.TileEntity

class FluidDrillBlock(props: Properties) : Block(props) {
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING)
    }
}

class FluidDrillTileEntity : TileEntity(fluidDrillTileEntity.unwrap()!!), ITickableTileEntity {
    override fun tick() {
    }
}