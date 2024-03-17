package kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator

import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

interface IGlyphConfiguratorHandler {

    val groups:     Array<GlyphGroup>
    var group:      GlyphGroup
    var glyphIndex: Int
    var glyph:      Glyph
    var fieldIndex: Int

    fun wrapGroup(way: WrapWay) { group = groups[when (groups.indexOf(group)) { 0 -> 1
                                                                                1 -> 0
                                                                                else -> throw Exception() }]    }
    fun wrapGlyph(way: WrapWay) { glyphIndex += way.value; glyph = group[glyphIndex].default()                  }
    fun wrapField(way: WrapWay) { fieldIndex += way.value                                                       }
    fun editField(way: WrapWay) { glyph = glyph.also { it.data.getEditors(glyph)[fieldIndex].editField(way) }   }
}