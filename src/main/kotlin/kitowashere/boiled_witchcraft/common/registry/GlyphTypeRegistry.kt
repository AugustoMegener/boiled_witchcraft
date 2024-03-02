package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.FireGlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegistryBuilder

object GlyphTypeRegistry {
    val glyphTypeRegistry: DeferredRegister<GlyphType<*>> = DeferredRegister.create(GlyphType.registryKey, ID)

    val fireGlyph = glyphTypeRegistry.register("fire_glyph") { -> FireGlyphType() }

    val glyphTypes = glyphTypeRegistry.makeRegistry { RegistryBuilder(GlyphType.registryKey) }

    object ResourceHelper {
        fun fromID(id: String) = glyphTypes.get(ResourceLocation.of(id, ':'))
        fun getID(glyph: GlyphType<*>) = glyphTypes.getResourceKey(glyph).map { it.registry().toString() }.orElseThrow()

        fun getGlyphTexture(glyphType: GlyphType<*>, size: Int = 1, relative: Boolean = false): ResourceLocation {
            if (size !in glyphType.sizes) throw Exception("Unavailable size for this glyph :/...")
            return glyphTypes.getKey(glyphType)!!
                .also { it.takeIf { !relative }?.withPrefix("textures/glyph/") }
                .withPrefix("${size}x${size}/")
                .withSuffix(".png")
        }
    }
}