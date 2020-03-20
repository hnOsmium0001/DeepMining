package io.github.hnosmium0001.deepmining

import io.github.hnosmium0001.deepmining.vein.CapabilityEventHandler
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.Mod

@Mod(DeepMining.MODID)
object DeepMining {
    const val MODID = "deepmining"

    init {
        FMLKotlinModLoadingContext.get().modEventBus.register(RegistryHandler)
        FMLKotlinModLoadingContext.get().modEventBus.register(CapabilityEventHandler)
    }
}

fun fromMod(path: String) = ResourceLocation(DeepMining.MODID, path)