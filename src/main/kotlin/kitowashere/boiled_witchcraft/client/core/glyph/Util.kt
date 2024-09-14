package kitowashere.boiled_witchcraft.client.core.glyph

import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

object Util {
    val Glyph.translatableName get(): MutableComponent =
        Component.translatable(GlyphRegistry.Util.getGlyphLocation(this).toLanguageKey("glyph") ?: "?")
}