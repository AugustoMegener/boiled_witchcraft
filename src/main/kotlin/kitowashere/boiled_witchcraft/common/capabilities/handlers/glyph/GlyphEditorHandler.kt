package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory

interface GlyphEditorHandler {
    val glyphCategories: Array<GlyphCategory>
    val glyphCategory: GlyphCategory
    val fieldIndex: Int

    val editor: GlyphEditor
}