package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph

object NoneGlyph : Glyph(arrayOf()) {
    override fun newData() = GlyphData(this)
}