package kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator

import kitowashere.boiled_witchcraft.common.data.handler.glyph.GlyphSelector
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

interface IGlyphConfiguratorHandler {
    var glyph: Glyph
    var fieldIndex: Int

    fun wrapGlyph(way: GlyphSelector.WrapWay)
    fun wrapField(way: GlyphSelector.WrapWay) { fieldIndex += way.value }
    fun editField(way: GlyphSelector.WrapWay) { glyph.data.getEditors(glyph.type)[fieldIndex].editField(way) }
}