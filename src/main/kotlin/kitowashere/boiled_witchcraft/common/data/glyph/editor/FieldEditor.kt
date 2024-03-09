package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.data.handler.glyph.GlyphSelector

abstract class FieldEditor<T>(field: GlyphData.DataField<T>) {
    protected var field by field

    abstract fun editField(way: GlyphSelector.WrapWay)
}