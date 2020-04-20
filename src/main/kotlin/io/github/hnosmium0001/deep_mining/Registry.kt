@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.github.hnosmium0001.deep_mining

import io.github.hnosmium0001.deep_mining.drill.FluidDrillBlock
import io.github.hnosmium0001.deep_mining.drill.FluidDrillTileEntity
import io.github.hnosmium0001.deep_mining.drill.MineralDrillBlock
import io.github.hnosmium0001.deep_mining.drill.MineralDrillTileEntity
import io.github.hnosmium0001.deep_mining.util.toBlockItem
import io.github.hnosmium0001.deep_mining.util.unwrap
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Supplier

val itemGroup = object : ItemGroup(DeepMining.MODID) {
    override fun createIcon() = ItemStack(mineralDrillItem.unwrap()!!)
}

fun itemProps(): Item.Properties = Item.Properties()
        .group(itemGroup)

val blockReg = DeferredRegister(ForgeRegistries.BLOCKS, DeepMining.MODID)
val tileReg = DeferredRegister(ForgeRegistries.TILE_ENTITIES, DeepMining.MODID)
val itemReg = DeferredRegister(ForgeRegistries.ITEMS, DeepMining.MODID)

val mineralDrillBlock: RegistryObject<MineralDrillBlock> =
        blockReg.register("mineral_drill") {
            MineralDrillBlock(Block.Properties
                    .create(Material.PISTON)
                    .hardnessAndResistance(2.0F, 10.0F))
        }
val mineralDrillTileEntity: RegistryObject<TileEntityType<MineralDrillTileEntity>> =
        tileReg.register("mineral_drill") {
            TileEntityType.Builder
                    .create<MineralDrillTileEntity>(Supplier { MineralDrillTileEntity() }, mineralDrillBlock.unwrap()!!)
                    .build(null)
        }
val mineralDrillItem: RegistryObject<BlockItem> =
        itemReg.register("mineral_drill") { mineralDrillBlock.unwrap()!!.toBlockItem() }

val fluidDrillBlock: RegistryObject<FluidDrillBlock> =
        blockReg.register("fluid_drill") {
            FluidDrillBlock(Block.Properties
                    .create(Material.PISTON)
                    .hardnessAndResistance(2.0F, 10.0F))
        }
val fluidDrillTileEntity: RegistryObject<TileEntityType<FluidDrillTileEntity>> =
        tileReg.register("fluid_drill") {
            TileEntityType.Builder
                    .create<FluidDrillTileEntity>(Supplier { FluidDrillTileEntity() }, fluidDrillBlock.unwrap()!!)
                    .build(null)
        }
val fluidDrillItem: RegistryObject<BlockItem> =
        itemReg.register("fluid_drill") { fluidDrillBlock.unwrap()!!.toBlockItem() }
