package kitowashere.boiled_witchcraft.common.core.editor

abstract class CompoundEditor(idx: Int) : Editor<Editor<*>>(idx) {

    constructor(idxs: List<Int>) : this(idxs[0]) {
        for ((idx, editor) in idxs.drop(0).zip(editors)) { editor.wrappedIndex = idx }
    }

    abstract val editors : List<Editor<*>>

    final override val maxIndex get() = editors.size

    override fun valueFromIndex(i: Int) = editors[i]
}