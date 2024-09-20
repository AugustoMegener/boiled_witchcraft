package kitowashere.boiled_witchcraft.common.core.editor

import kitowashere.boiled_witchcraft.common.util.Wrapable

abstract class Editor<T : Any>(idx: Int) : Wrapable {
    final override var wrappedIndex = idx
        set(v) { field = 0; value = valueFromIndex(v) }

    lateinit var value: T private set

    abstract fun valueFromIndex(i: Int): T
}