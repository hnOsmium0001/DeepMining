package io.github.hnosmium0001.deepmining.util

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.ListNBT

inline fun <T> pack(list: Collection<T>, packer: (T) -> CompoundNBT) = ListNBT().apply {
    for (element in list) {
        add(packer(element))
    }
}

inline fun <T> pack(list: Array<T>, packer: (T) -> CompoundNBT) = ListNBT().apply {
    for (element in list) {
        add(packer(element))
    }
}

inline fun <T> ListNBT.unpack(result: MutableCollection<T>, unpacker: CompoundNBT.() -> T) = result.also {
    for (compound in this) {
        result.add(unpacker(compound as CompoundNBT))
    }
}

inline fun <T> ListNBT.unpackUse(unpacker: CompoundNBT.() -> T) {
    for (compound in this) {
        unpacker(compound as CompoundNBT)
    }
}