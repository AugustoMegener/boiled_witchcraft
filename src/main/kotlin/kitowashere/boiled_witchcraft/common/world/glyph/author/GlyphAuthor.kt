package kitowashere.boiled_witchcraft.common.world.glyph.author

import io.kito.kore.common.data.Save
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthorType.Companion.glyphAuthorTypeCodec
import net.minecraft.world.level.Level

interface GlyphAuthor {
    @Save
    val type: GlyphAuthorType<*>

    val compositions: List<GlyphStack>

    val avaliableGlyphs: List<Glyph<*>>
    val level: Level

    fun addComposition(stack: GlyphStack)
    fun removeComposition(idx: Int)

    companion object {
        val glyphAuthorCodec =
            glyphAuthorTypeCodec().dispatch({ it: GlyphAuthor -> it.type }) { it.codec }
    }
}