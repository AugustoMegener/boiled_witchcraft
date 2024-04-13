package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import org.joml.Vector2i

abstract class Glyph(val sizes: Array<Int>) {

    abstract fun newData(): GlyphData
    open fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>()

    companion object {
        val placeholder = FireGlyph
    }
}