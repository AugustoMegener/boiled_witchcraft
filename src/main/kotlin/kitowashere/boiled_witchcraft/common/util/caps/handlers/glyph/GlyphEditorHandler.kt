package kitowashere.boiled_witchcraft.common.util.caps.handlers.glyph

import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory
import kitowashere.boiled_witchcraft.common.world.glyph.data.editor.GlyphEditor

interface GlyphEditorHandler {
    val glyphCategories: Array<GlyphCategory>
    val glyphCategory: GlyphCategory
    val fieldIndex: Int

    val editor: GlyphEditor
}