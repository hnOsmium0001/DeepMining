package io.github.hnosmium0001.deepmining

import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.fml.common.Mod

@Mod(DeepMining.MODID)
object DeepMining {
    const val MODID = "deepmining"

    init {
        FMLKotlinModLoadingContext.get().modEventBus.register(RegistryHandler)
    }
}