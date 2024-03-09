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
        val primaries       get() = glyphTypes.filter { it.kind == GlyphKind.PRIMARY    }
        val structurals     get() = glyphTypes.filter { it.kind == GlyphKind.STRUCTURAL }

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
}

