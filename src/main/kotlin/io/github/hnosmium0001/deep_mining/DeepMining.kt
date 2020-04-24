package io.github.hnosmium0001.deep_mining

import io.github.hnosmium0001.deep_mining.vein.CapabilityEventHandler
import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig

@Mod(DeepMining.MODID)
object DeepMining {
    const val MODID = "deep_mining"

    init {
        ModLoadingContext.get().run {
            registerConfig(ModConfig.Type.COMMON, CommonConfig.spec)
            registerConfig(ModConfig.Type.CLIENT, ClientConfig.spec)
        }
        FMLKotlinModLoadingContext.get().modEventBus.run {
            register(CapabilityEventHandler)
            blockReg.register(this)
            tileReg.register(this)
            itemReg.register(this)
        }
    }
}