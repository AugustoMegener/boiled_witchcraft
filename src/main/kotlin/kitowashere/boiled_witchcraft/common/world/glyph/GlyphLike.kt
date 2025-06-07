package kitowashere.boiled_witchcraft.common.world.glyph

interface GlyphLike {

    val glyph: Glyph<*>

    fun asStack(): GlyphStack
}