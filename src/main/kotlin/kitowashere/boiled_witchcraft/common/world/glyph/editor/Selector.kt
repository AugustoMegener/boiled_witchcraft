package kitowashere.boiled_witchcraft.common.world.glyph.editor

import kitowashere.boiled_witchcraft.coerceInverse

interface Selector {

    var index: Int

    val range: IntRange

    fun next() {
        index = (index+1).coerceInverse(range)
        update()
    }

    fun prev() {
        index = (index-1).coerceInverse(range)
        update()
    }

    fun update()
}