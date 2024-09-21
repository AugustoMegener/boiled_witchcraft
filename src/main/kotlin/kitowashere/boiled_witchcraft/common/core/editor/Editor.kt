package kitowashere.boiled_witchcraft.common.core.editor

import kitowashere.boiled_witchcraft.common.core.Selector
import net.minecraft.resources.ResourceLocation

abstract class Editor(location: ResourceLocation) : Selector<Selector<*>>(location) {

    abstract val selectors : List<Selector<*>>

    final override val maxIndex get() = selectors.size

    override fun valueFromIndex(i: Int) = selectors[i]

    fun encode() : IntArray = intArrayOf(wrappedIndex) + selectors.map { it.wrappedIndex }

    fun parse(idxs: IntArray)
        { wrappedIndex = idxs[0]; idxs.drop(0).zip(selectors).forEach { it.second.wrappedIndex = it.first } }
}