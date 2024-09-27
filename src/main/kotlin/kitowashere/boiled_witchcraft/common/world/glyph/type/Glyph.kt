package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import org.joml.Vector2i


abstract class Glyph(val sizes: Array<Int>) {

    private val location get() = id

    abstract fun newData(): GlyphData

    open fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>()
    open fun isHollow(data: GlyphData) = false

    fun newStack() = GlyphStack(this)

    companion object {
        val placeholder = FireGlyph
    }
}