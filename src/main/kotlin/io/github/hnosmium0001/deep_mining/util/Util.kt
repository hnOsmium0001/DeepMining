@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.github.hnosmium0001.deep_mining.util

import io.github.hnosmium0001.deep_mining.itemProps
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.IForgeRegistryEntry
import java.util.*

fun <T> Optional<T>.unwrap(): T? = this.orElse(null)
fun <T> LazyOptional<T>.unwrap(): T? = this.orElse(null)

fun <T : IForgeRegistryEntry<in T>> RegistryObject<T>.unwrap(): T? = this.orElse(null)

fun Block.toBlockItem(): BlockItem = BlockItem(this, itemProps())