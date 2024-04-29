package kitowashere.boiled_witchcraft.client.core.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

object Util {
    val Glyph.translatableName get(): MutableComponent =
        Component.translatable(GlyphRegistry.Util.getGlyphLocation(this)?.toLanguageKey("glyph") ?: "?")
}