package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.FireGlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.RegistryBuilder

object GlyphTypeRegistry {
    val glyphTypeRegistry: DeferredRegister<GlyphType<*>> = DeferredRegister.create(GlyphType.registryKey, ID)

    val fireGlyph = glyphTypeRegistry.register("fire_glyph") { -> FireGlyphType() }

    val glyphTypes = glyphTypeRegistry.makeRegistry { RegistryBuilder(GlyphType.registryKey) }
}