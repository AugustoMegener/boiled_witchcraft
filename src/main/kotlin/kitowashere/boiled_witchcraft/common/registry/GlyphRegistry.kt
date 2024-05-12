package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.GlyphCategory.Companion.onCategories
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.GlyphCategory.Companion.primaries
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.GlyphCategory.Companion.structurals
import kitowashere.boiled_witchcraft.common.world.glyph.FireGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.RingGlyph
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegistryBuilder

object GlyphRegistry {

    private val registryKey: ResourceKey<Registry<Glyph>> =
        ResourceKey.createRegistryKey(ResourceLocation("glyphs"))

    val glyphRegistry: DeferredRegister<Glyph> = DeferredRegister.create(registryKey, ID)


    val fireGlyph: DeferredHolder<Glyph, FireGlyph> =
        glyphRegistry.register("fire_glyph") { -> FireGlyph.onCategories(primaries) }

    val ringGlyph: DeferredHolder<Glyph, RingGlyph> =
        glyphRegistry.register("ring_glyph") { -> RingGlyph.onCategories(structurals) }


    val glyphTypes: Registry<Glyph> = glyphRegistry.makeRegistry { RegistryBuilder(registryKey) }

    object Util {
        fun glyphFromID(id: String) = glyphTypes[ResourceLocation.of(id, ':')]

        val Glyph.id get() = glyphTypes.getKey(this)!!.toString()

        fun getGlyphLocation(glyphType: Glyph) = glyphTypes.getKey(glyphType)
    }

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
}