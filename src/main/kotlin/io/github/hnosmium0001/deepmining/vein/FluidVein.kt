package io.github.hnosmium0001.deepmining.vein

import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fluids.FluidStack

class FluidVein {
}

data class Fluid(val fluid: FluidStack, val weight: Float) {
    fun write(tag: CompoundNBT = CompoundNBT()): CompoundNBT {
        tag.put("Fluid", fluid.writeToNBT(CompoundNBT()))
        tag.putFloat("Weight", weight)
        return tag
    }
}