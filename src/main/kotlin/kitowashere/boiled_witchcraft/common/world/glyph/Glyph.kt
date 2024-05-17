package kitowashere.boiled_witchcraft.common.world.glyph

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import org.joml.Vector2i

abstract class Glyph(val sizes: Array<Int>) {

    abstract fun newData(): GlyphData

    open fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>()
    open fun isHollow(data: GlyphData) = false

    companion object {
        val placeholder = FireGlyph

        val codec: Codec<Glyph> = GlyphRegistry.glyphs.byNameCodec()
    }
}