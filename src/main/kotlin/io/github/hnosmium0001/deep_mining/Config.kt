package io.github.hnosmium0001.deep_mining

import net.minecraftforge.common.ForgeConfigSpec

object CommonConfig {
    private val builder = ForgeConfigSpec.Builder()

    val mineralDrillPerformance = builder
        .define("mineralDrillPerformance", 1)

    val spec = builder.build()
}

object ClientConfig {
    private val builder = ForgeConfigSpec.Builder()

    val spec = builder.build()
}