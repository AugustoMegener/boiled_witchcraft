package kitowashere.boiled_witchcraft.common.data.handler.glyph.composing

import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCanvas

interface IGlyphComposingHandler {
    fun withComposing(action: (GlyphCanvas) -> Unit)
}