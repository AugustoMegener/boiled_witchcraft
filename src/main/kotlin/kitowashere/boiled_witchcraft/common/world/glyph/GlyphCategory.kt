package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

data class GlyphCategory(val location: ResourceLocation) {
    private val glyphs = ArrayList<Glyph>()

    val name: MutableComponent = Component.translatable(location.toLanguageKey("glyph.group"))
    val size get() = glyphs.size

    operator fun get(i: Int): Glyph = glyphs[i]
    operator fun contains(i: Glyph?) = i != null && i in glyphs

    companion object {
        val primaries   = GlyphCategory(ResourceLocation(ID, "primaries"))
        val structurals = GlyphCategory(ResourceLocation(ID, "structurals"))

        fun <T: Glyph> T.onCategories(vararg categories: GlyphCategory): T {
            categories.forEach { it.glyphs += this }
            return this
        }
    }
}