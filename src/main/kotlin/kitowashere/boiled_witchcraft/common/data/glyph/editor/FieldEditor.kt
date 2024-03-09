package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.data.handler.glyph.GlyphSelector
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

abstract class FieldEditor<T>(field: GlyphData.DataField<T>, protected val glyph: Glyph) {
    protected var field by field

    abstract fun editField(way: GlyphSelector.WrapWay)
}