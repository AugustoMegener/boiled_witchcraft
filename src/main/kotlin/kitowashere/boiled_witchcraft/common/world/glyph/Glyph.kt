package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import org.joml.Vector2i

abstract class Glyph(val sizes: Array<Int>) {

    open val signal = HashMap<Vector2i, Boolean>()

    abstract fun newData(): GlyphData

    companion object {
        val placeholder = FireGlyph
    }
}