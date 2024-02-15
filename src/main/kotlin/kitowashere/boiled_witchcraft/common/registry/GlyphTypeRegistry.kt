package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.type.FireGlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.neoforged.neoforge.registries.DeferredRegister

object GlyphTypeRegistry {
    val glyphTypes: DeferredRegister<GlyphType> = DeferredRegister.create(GlyphType.registryKey, ID)

    val fireGlyph = glyphTypes.register("fire_glyph")
    { -> FireGlyphType() }
}