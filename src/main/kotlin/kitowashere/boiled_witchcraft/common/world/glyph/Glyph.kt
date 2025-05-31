package kitowashere.boiled_witchcraft.common.world.glyph

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import io.kito.kore.common.data.codec.CodecSource
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.registry.Registries.haveGlyphRegistry
import org.joml.Vector2i

abstract class Glyph<T : GlyphData>(val sizes: List<Int>) {

    abstract fun createData(): T

    abstract fun dataCodec(): MapCodec<T>

    abstract fun isHollow(data: T): Boolean

    abstract fun canLinkOn(pos: Vector2i, data: T): Boolean

    @Suppress(UNCHECKED_CAST)
    fun cast(data: GlyphData) = data as T

    fun stack() = GlyphStack(this)

    override fun toString() = if (haveGlyphRegistry) glyphRegistry.getKey(this).toString() else "glyph"

    @Scan
    companion object {
        @CodecSource
        fun glyphCodec(): Codec<Glyph<*>> = glyphRegistry.byNameCodec()

        @CodecSource
        fun glyphStackCodec() = GlyphStack.codec
    }
}