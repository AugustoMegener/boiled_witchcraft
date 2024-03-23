package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.FireGlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.GlyphKind
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegistryBuilder


object GlyphTypeRegistry {
    val glyphTypeRegistry: DeferredRegister<GlyphType> = DeferredRegister.create(GlyphType.registryKey, ID)

    val fireGlyph = glyphTypeRegistry.register("fire_glyph") { -> FireGlyphType() }

    val glyphTypes: Registry<GlyphType> = glyphTypeRegistry.makeRegistry { RegistryBuilder(GlyphType.registryKey) }

    object Util {

        fun glyphTypeFromID(id: String) = glyphTypes.get(ResourceLocation.of(id, ':'))

        fun glyphTypeID(glyph: GlyphType)  = glyphTypes.getKey(glyph)!!.toString()

        fun getGlyphTexture(glyphType: GlyphType, size: Int = 1): ResourceLocation {
            if (size !in glyphType.sizes) throw Exception("Unavailable size for this glyph :/...")
            return getGlyphLocation(glyphType)!!.withPrefix("textures/glyph/${size}x${size}/")
                                                .withSuffix(".png")
        }

        fun translatableName(glyph: GlyphType) =
            Component.translatable(getGlyphLocation(glyph)!!.toLanguageKey("glyph"))

        private fun getGlyphLocation(glyphType: GlyphType) = glyphTypes.getKey(glyphType)

    }

    data class GlyphGroup(val location: ResourceLocation, private val filter: (GlyphType) -> Boolean) {
        val glyphs get() = glyphTypes.filter(filter)
        val name = location.toLanguageKey("glyph.group")

        operator fun get(i: Int): GlyphType = glyphs[i]
        operator fun contains(i: GlyphType?) = i != null && i in glyphs

        companion object {
            val primaries   = GlyphGroup(ResourceLocation(ID, "primaries"))   { it.kind == GlyphKind.PRIMARY    }
            val structurals = GlyphGroup(ResourceLocation(ID, "structurals")) { it.kind == GlyphKind.STRUCTURAL }
        }
    }
}

