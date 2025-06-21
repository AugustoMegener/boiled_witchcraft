package kitowashere.boiled_witchcraft.common.world.glyph.editor.input

sealed class SelectorInput(val value: Int) : EditorInput {

    data object Next : SelectorInput( 1)
    data object Prev : SelectorInput(-1)

    companion object {
        fun of(value: Int) =
            when {
                value > 0 -> Next
                value < 0 -> Prev
                else -> throw IllegalStateException("Invalid zero value for selector input")
            }
    }
}