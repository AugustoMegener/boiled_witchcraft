package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor

@GlyphEditorKit("$ID:pillar_option_kit")
@RegisterGlyph("fire_glyph")
object FireGlyph : PillarGlyph<PillarGlyphData>(listOf(1, 2, 3)) {

    override val isPrimary = true

    override fun createData() = PillarGlyphData(this)

    override fun dataCodec() = PillarGlyphData.mapCodec
    override fun dataStreamCodec() = PillarGlyphData.streamCodec

    override fun maxHeight(user: GlyphAuthor, data: PillarGlyphData) = 5
}