package kitowashere.boiled_witchcraft.common.data.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.editor.IntFieldEditor

class PillarGlyphData : GlyphData() {
    var height by DataField("height", 1) // TODO: Dynamic height
               { field, glyph -> IntFieldEditor(field, glyph) {_, value, _ -> value.coerceIn(1, 5)} }
}