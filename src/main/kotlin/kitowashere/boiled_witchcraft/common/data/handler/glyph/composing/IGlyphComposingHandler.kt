package kitowashere.boiled_witchcraft.common.data.handler.glyph.composing

import kitowashere.boiled_witchcraft.common.world.glyph.GlyphComposing

interface IGlyphComposingHandler {
    fun withComposing(action: (GlyphComposing) -> Unit)
}