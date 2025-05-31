package kitowashere.boiled_witchcraft.common.world.glyph.editor.input

sealed class SelectorInput(val value: Int) : EditorInput {

    data object Next : SelectorInput( 1)
    data object Prev : SelectorInput(-1)
}