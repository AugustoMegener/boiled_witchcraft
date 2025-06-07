package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor

abstract class PillarGlyph<T : PillarGlyphData>(sizes: List<Int>) : Glyph<T>(sizes) {

    open fun minHeight(user: GlyphAuthor, data: T) = 1
    abstract fun maxHeight(user: GlyphAuthor, data: T) : Int
}