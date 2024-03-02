package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType

class EditingGlyph : GlyphInstanceHandler {

    override var type: GlyphType<*> = GlyphType.placeholder
        set(value) {
            if (value.kind != GlyphType.GlyphKind.PRIMARY) throw Exception("Only primary glyphs can be accepted here.")
            field = value
            data = value.newData()
        }
    override var data: GlyphData = type.newData()

    private var index = 0
        set(value) {
            field = value
            type = primaries[field]
        }

    enum class WrapWay(val value: Int) { PRIOR(-1), NEXT(1) }

    fun wrapGlyph(way: WrapWay) { index += way.value }
}