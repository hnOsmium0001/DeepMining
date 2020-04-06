package io.github.hnosmium0001.deepmining.vein

import io.github.hnosmium0001.deepmining.DeepMining
import io.github.hnosmium0001.deepmining.util.pack
import io.github.hnosmium0001.deepmining.util.unpack
import io.github.hnosmium0001.deepmining.util.unpackUse
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.util.Constants
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

abstract class Vein<T> {
    val chunks: MutableMap<ChunkPos, MutableList<T>> = HashMap()
}

class MineralVeins : Vein<MineralVein>()

@CapabilityInject(MineralVeins::class)
lateinit var CAPABILITY_MINERAL_VEINS: Capability<MineralVeins>

fun registerMineral() {
    CapabilityManager.INSTANCE.register(MineralVeins::class.java, object : Capability.IStorage<MineralVeins> {
        override fun readNBT(capability: Capability<MineralVeins>, instance: MineralVeins, side: Direction?, nbt: INBT) {
            if (nbt !is CompoundNBT) {
                throw IllegalArgumentException("Reading from an invalid NBT type: ${nbt.javaClass}")
            }

            instance.chunks.clear()
            nbt.getList("Chunks", Constants.NBT.TAG_COMPOUND).unpackUse {
                val pos = ChunkPos(getLong("Pos"))
                val veins = getList("Veins", Constants.NBT.TAG_COMPOUND).unpack(ArrayList()) { MineralVein.read(this) } as MutableList
                instance.chunks.put(pos, veins)
            }
        }

        override fun writeNBT(capability: Capability<MineralVeins>, instance: MineralVeins, side: Direction?): INBT? {
            return CompoundNBT().apply {
                put("Chunks", pack(instance.chunks.entries) { entry ->
                    CompoundNBT().apply {
                        putLong("Pos", entry.key.asLong())
                        put("Veins", pack(entry.value) { m -> m.write() })
                    }
                })
            }
        }
    }, ::MineralVeins)
}

class FluidVeins : Vein<FluidVein>()

@CapabilityInject(FluidVeins::class)
lateinit var CAPABILITY_FLUID_VEINS: Capability<FluidVeins>

fun registerFluid() {
    CapabilityManager.INSTANCE.register(FluidVeins::class.java, object : Capability.IStorage<FluidVeins> {
        override fun readNBT(capability: Capability<FluidVeins>, instance: FluidVeins, side: Direction?, nbt: INBT) {
            // TODO
        }

        override fun writeNBT(capability: Capability<FluidVeins>, instance: FluidVeins, side: Direction?): INBT? {
            // TODO
            return CompoundNBT()
        }
    }, ::FluidVeins)
}


object CapabilityEventHandler {
    @SubscribeEvent
    fun attachCaps(event: AttachCapabilitiesEvent<World>) {
        event.addCapability(ResourceLocation(DeepMining.MODID, "veins"), object : ICapabilityProvider {
            val mineralVeins = LazyOptional.of { MineralVeins() }
            val fluidVeins = LazyOptional.of { FluidVeins() }

            @Suppress("DUPLICATE_LABEL_IN_WHEN") // Actual values are injected during runtime
            override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> =
                    when (cap) {
                        CAPABILITY_MINERAL_VEINS -> mineralVeins.cast()
                        CAPABILITY_FLUID_VEINS -> fluidVeins.cast()
                        else -> LazyOptional.empty()
                    }
        })
    }
}