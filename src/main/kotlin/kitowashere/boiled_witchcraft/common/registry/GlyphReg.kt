package kitowashere.boiled_witchcraft.common.registry

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.register
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation.parse
import net.neoforged.neoforge.registries.RegistryBuilder

object GlyphReg {
    val glyphs: ResourceKey<Registry<Glyph>> =
        ResourceKey.createRegistryKey(parse("$ID:glyphs"))

    val glyphRegistry: Registry<Glyph> = register.makeRegistry { RegistryBuilder(glyphs) }

    val codec: Codec<Glyph> = glyphRegistry.byNameCodec()
}