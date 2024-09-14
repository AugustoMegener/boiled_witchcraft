package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import org.joml.Vector2i
import kotlin.math.abs

object RingGlyph : Glyph(Array(8) { it + 2}) {
    override fun newData() = GlyphData(this)

    override fun isHollow(data: GlyphData) = true

    override fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>().also {
        val size = data.size
        val radius = size / 2

        for (x in 0..<size) for (y in 0..<size) {
            val d = abs(x - radius) + abs(y - radius)
            if (d == radius || d == radius - 1) it[Vector2i(x, y)] = true
        }
    }
}