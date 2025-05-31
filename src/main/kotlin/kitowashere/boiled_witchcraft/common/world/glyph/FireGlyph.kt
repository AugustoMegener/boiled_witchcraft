package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser
import org.joml.Vector2i

@GlyphEditorKit("$ID:pillar_option_kit")
@RegisterGlyph("fire")
object FireGlyph : PillarGlyph<PillarGlyphData>(listOf(1, 2, 3)) {

    override fun createData() = PillarGlyphData(this)

    override fun dataCodec() = PillarGlyphData.mapCodec

    override fun canLinkOn(pos: Vector2i, data: PillarGlyphData): Boolean = false

    override fun isHollow(data: PillarGlyphData) = false

    override fun maxHeight(user: EditorUser, data: PillarGlyphData) = 5
}