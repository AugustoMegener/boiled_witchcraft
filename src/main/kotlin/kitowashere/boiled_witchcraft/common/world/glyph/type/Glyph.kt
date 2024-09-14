package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import org.joml.Vector2i


abstract class Glyph(val sizes: Array<Int>) {

    private val location get() = id

    abstract fun newData(): GlyphData

    open fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>()
    open fun isHollow(data: GlyphData) = false

    companion object {
        val placeholder = FireGlyph
    }
}