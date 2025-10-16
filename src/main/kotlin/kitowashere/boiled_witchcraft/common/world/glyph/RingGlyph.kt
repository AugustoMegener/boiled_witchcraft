package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import org.joml.Vector2i

@GlyphEditorKit("$ID:simple_option_kit")
@RegisterGlyph("ring_glyph")
object RingGlyph : Glyph<GlyphData>(listOf(2, 3, 4, 5)) {

    override val isLinkable = true

    override val isPrimary = true

    override fun isHollow(data: GlyphData) = true

    override fun createData() = GlyphData(this)

    override fun dataCodec() = GlyphData.mapCodec
    override fun dataStreamCodec() = GlyphData.streamCodec

    override fun canLinkOn(pos: Vector2i, data: GlyphData): Boolean {
        val (x, y) = pos.x to pos.y
        return when (data.size) {
            2 -> true
            3 -> (x == 1 && y == 0) ||
                 (x == 0 && y == 1) ||
                 (x == 2 && y == 1) ||
                 (x == 1 && y == 2)
            4 -> (x == 1 && y == 0) || (x == 2 && y == 0) ||
                 (x == 0 && y == 1) || (x == 3 && y == 1) ||
                 (x == 0 && y == 2) || (x == 3 && y == 2) ||
                 (x == 1 && y == 3) || (x == 2 && y == 3)
            5 -> (y == 0 && x in 1..3) ||
                 (y == 4 && x in 1..3) ||
                 (x == 0 && y in 1..3) ||
                 (x == 4 && y in 1..3)
            else -> false
        }
    }
}