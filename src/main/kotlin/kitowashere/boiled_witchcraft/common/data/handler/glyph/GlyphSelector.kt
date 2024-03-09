package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.primaries
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

class GlyphSelector : IGlyphHandler {

    override var glyph = Glyph.placeholder
    private var index = 0; set(value) { field = value; glyph = primaries[field].default() }

    enum class WrapWay(val value: Int) { PRIOR(-1), NEXT(1) }
    fun wrapGlyph(way: WrapWay) { index += way.value }
}