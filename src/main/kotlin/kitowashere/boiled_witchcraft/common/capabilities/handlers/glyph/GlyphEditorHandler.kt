package kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph

import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import kitowashere.boiled_witchcraft.common.util.KotlinUtil.Delegation
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack

interface GlyphEditorHandler {
    val glyphSource: Delegation<GlyphStack>
    val editor: GlyphEditor
}