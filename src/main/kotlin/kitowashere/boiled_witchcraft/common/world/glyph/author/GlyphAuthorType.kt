package kitowashere.boiled_witchcraft.common.world.glyph.author

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import io.kito.kore.common.data.codec.CodecSource
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphAuthorTypeRegistry

@JvmInline
value class GlyphAuthorType<T : GlyphAuthor>(val codec: MapCodec<T>) {

    @Scan
    companion object {

        @CodecSource
        fun glyphAuthorTypeCodec(): Codec<GlyphAuthorType<*>> = glyphAuthorTypeRegistry.byNameCodec()

        @CodecSource
        fun glyphAuthorCodec(): Codec<GlyphAuthor> = GlyphAuthor.glyphAuthorCodec
    }
}