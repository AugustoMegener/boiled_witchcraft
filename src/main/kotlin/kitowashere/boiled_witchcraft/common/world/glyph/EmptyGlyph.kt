package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData

@GlyphEditorKit("$ID:simple_option_kit")
@RegisterGlyph("empty")
object EmptyGlyph : Glyph<GlyphData>(listOf(1)) {

    override fun createData() = GlyphData(this)

    override fun dataCodec() = GlyphData.mapCodec
}