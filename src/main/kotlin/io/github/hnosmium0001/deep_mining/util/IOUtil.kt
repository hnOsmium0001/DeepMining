package io.github.hnosmium0001.deep_mining.util

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.ListNBT

inline fun <T> Iterable<T>.pack(packer: (T) -> INBT) =
    ListNBT().also { result ->
        for (element in this) {
            result.add(packer.invoke(element))
        }
    }

inline fun <T> Array<T>.pack(packer: (T) -> INBT) =
    ListNBT().also { result ->
        for (element in this) {
            result.add(packer.invoke(element))
        }
    }

inline fun <K, V> Map<K, V>.packEntry(packer: (K, V) -> CompoundNBT) =
    ListNBT().also { result ->
        for ((key, value) in this) {
            result.add(packer.invoke(key, value))
        }
    }

inline fun <K, V> Map<K, V>.pack(packer: (V) -> INBT) =
    this.packEntry { key, value ->
        CompoundNBT().also { entry ->
            entry.putString("Key", key.toString())
            entry.put("Value", packer.invoke(value))
        }
    }

inline fun <T, S : MutableCollection<T>, reified C : INBT> ListNBT.unpack(result: S, unpacker: (C) -> T) =
    result.also {
        for (compound in this) {
            result.add(unpacker(compound as C))
        }
    }

//inline fun <K, V, reified C : INBT> ListNBT.unpack(result: Map<K, V>)