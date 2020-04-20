package io.github.hnosmium0001.deep_mining

import io.github.hnosmium0001.deep_mining.vein.CapabilityEventHandler
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.fml.common.Mod

@Mod(DeepMining.MODID)
object DeepMining {
    const val MODID = "deep_mining"

    init {
        val bus = FMLKotlinModLoadingContext.get().modEventBus
        bus.register(CapabilityEventHandler)
        blockReg.register(bus)
        tileReg.register(bus)
        itemReg.register(bus)
    }
}