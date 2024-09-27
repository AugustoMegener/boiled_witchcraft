package kitowashere.boiled_witchcraft.common.core.editor

import kitowashere.boiled_witchcraft.common.core.Select
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

abstract class Editor<R> : Select<Editor<R>.EditorSelect<*>>() {
    abstract var result: R

    abstract inner class EditorSelect<T> : Select<T>() {
        abstract val location: ResourceLocation

        abstract val  nameComponent: MutableComponent
        abstract val valueComponent: MutableComponent

        final override fun wrap(way: WrapWay) {
            super.wrap(way)
            result = newResult(result)
        }

        abstract fun newResult(old: R) : R
    }
}