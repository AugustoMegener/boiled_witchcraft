package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

open class IntFieldEditor(
    field: GlyphData.DataField<Int>, glyph: Glyph,
    private val validator: (Glyph, Int, WrapWay) -> Int
) : FieldEditor<Int>(field, glyph)
{
    private fun validate(value: Int, way: WrapWay) = validator.invoke(glyph, value, way)

    override fun editField(way: WrapWay) { field = validate(field + way.value, way) }
}