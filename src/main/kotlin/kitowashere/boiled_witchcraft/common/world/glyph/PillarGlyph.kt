package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser

abstract class PillarGlyph<T : PillarGlyphData>(sizes: List<Int>) : Glyph<T>(sizes) {

    open fun minHeight(user: EditorUser, data: T) = 1
    abstract fun maxHeight(user: EditorUser, data: T) : Int
}