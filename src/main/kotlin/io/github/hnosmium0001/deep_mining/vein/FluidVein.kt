package io.github.hnosmium0001.deep_mining.vein

import net.minecraftforge.fluids.FluidStack

// TODO
class FluidVein {
}

class FluidComposition(val fluids: Array<Fluid>) {
}

data class Fluid(val fluid: FluidStack, val weight: Float)