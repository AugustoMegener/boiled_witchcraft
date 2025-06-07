package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import org.joml.Vector2i
import kotlin.math.abs

@GlyphEditorKit("$ID:simple_option_kit")
@RegisterGlyph("ring_glyph")
object RingGlyph : Glyph<GlyphData>(listOf(2, 3, 4, 5)) {

    override val isLinkable = true

    override val isPrimary = true

    override fun isHollow(data: GlyphData) = true

    override fun createData() = GlyphData(this)

    override fun dataCodec() = GlyphData.mapCodec

    override fun canLinkOn(pos: Vector2i, data: GlyphData): Boolean {
        val size = data.size
        val radius = size / 2
        val d = abs(pos.x - radius) + abs(pos.y - radius)
        return d == radius || d == radius - 1
    }
}