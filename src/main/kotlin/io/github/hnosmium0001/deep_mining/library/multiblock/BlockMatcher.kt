package io.github.hnosmium0001.deep_mining.library.multiblock

import net.minecraft.block.BlockState

interface BlockMatcher {
    fun matches(state: BlockState): Boolean
}

class ListedBlockMatcher(val matchingStates: Set<BlockState>) : BlockMatcher {
    constructor(vararg states: BlockState)
        : this(matchingStates = HashSet<BlockState>().apply { addAll(states) })

    override fun matches(state: BlockState): Boolean = matchingStates.contains(state)
}