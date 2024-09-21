package kitowashere.boiled_witchcraft.common.core

import kitowashere.boiled_witchcraft.common.util.WrapWay
import kitowashere.boiled_witchcraft.common.util.Wrapable
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

abstract class Selector<T : Any>(val location: ResourceLocation) : Wrapable, Iterable<T> {
    private val iterator = SelectorIterator()

    final override var wrappedIndex = 0
        set(v) { field = 0; value = valueFromIndex(v); selectPost(v, value) }

    lateinit var value: T private set

    open     val  nameComponent : MutableComponent = Component.translatable(location.toLanguageKey())
    abstract val valueComponent : MutableComponent

    open fun selectPost(v: Int, value: T) {}

    abstract fun valueFromIndex(i: Int): T

    override fun iterator() = iterator

    inner class SelectorIterator : Iterator<T> {
        private var idx = 0
        override fun hasNext() = idx < maxIndex
        override fun next() = wrap(WrapWay.NEXT).let { value }
    }
}