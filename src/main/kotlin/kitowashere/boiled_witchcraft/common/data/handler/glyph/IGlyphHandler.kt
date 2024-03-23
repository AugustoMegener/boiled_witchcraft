package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

interface IGlyphHandler {
    var glyph: Glyph

    fun set(new: Glyph) {
        glyph = new
        onChanged()
    }

    fun perform(action: (Glyph) -> Unit) {
        action(glyph)
        onChanged()
    }

    fun onChanged()
}