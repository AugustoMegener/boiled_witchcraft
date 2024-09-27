package kitowashere.boiled_witchcraft.common.world.glyph

import com.google.common.collect.ImmutableList
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse

class GlyphCategory(val location: ResourceLocation) {
    private val glyphs = ArrayList<Glyph>()

    val content: List<Glyph> by lazy { ImmutableList.copyOf(glyphs) }

    val name: MutableComponent = Component.translatable(location.toLanguageKey("glyph.group"))
    val size get() = glyphs.size


    operator fun get(i: Int): Glyph = glyphs[i]
    operator fun contains(i: Glyph?) = i != null && i in glyphs

    companion object {
        private val categoriesList = listOf<GlyphCategory>()
        val categories by lazy { categoriesList.toTypedArray() }

        val primaries   = GlyphCategory(parse(("$ID:primaries")))
        val structurals = GlyphCategory(parse(("$ID:structurals")))

        fun <T: Glyph> T.onCategories(vararg categories: GlyphCategory): T {
            categories.forEach { it.glyphs += this }
            return this
        }
    }
}