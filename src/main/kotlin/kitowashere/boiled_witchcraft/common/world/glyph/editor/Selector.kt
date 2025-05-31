package kitowashere.boiled_witchcraft.common.world.glyph.editor

interface Selector {

    var index: Int

    val range: IntRange

    fun next() {
        index = (index+1).coerceIn(range)
        update()
    }

    fun prev() {
        index = (index-1).coerceIn(range)
        update()
    }

    fun update()
}