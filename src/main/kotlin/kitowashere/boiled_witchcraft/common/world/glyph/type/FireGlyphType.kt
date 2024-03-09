package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData

class FireGlyphType : GlyphType(GlyphKind.PRIMARY, listOf(1, 2, 3)) {
    override fun newData(): PillarGlyphData = PillarGlyphData()
}