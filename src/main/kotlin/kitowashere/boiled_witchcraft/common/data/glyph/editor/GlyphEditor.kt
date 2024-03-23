package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.common.data.handler.glyph.IGlyphHandler
import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph

abstract class GlyphEditor(private val glyphHandler: IGlyphHandler) {
    abstract val groups: Array<GlyphGroup>

     var group: GlyphGroup = groups.run {
         for (i in 0..groups.size) if (glyphHandler.glyph.type in groups[i]) return@run groups[i]
         groups[0]
     }
        private set(value) {
            field = value
            glyph = value[0].default()
        }
    var glyph: Glyph = glyphHandler.glyph.takeIf { it.type in group } ?: group[0].default()
        private set(value) {
            field = value
            glyphHandler.set(value)
            fieldIndex = 0
        }

    var fieldIndex: Int = 0; private set

    fun wrapGroup(way: WrapWay) { group = groups[getIndex(groups.size, groups.indexOf(group)+way.value)] }

    fun wrapGlyph(way: WrapWay) {
        glyph = group[getIndex(group.glyphs.size, group.glyphs.indexOf(glyph.type)+way.value)].default()
    }

    fun wrapField(way: WrapWay) {
        fieldIndex = getIndex(glyph.data.editorAmount, fieldIndex+way.value)
    }

    companion object {
        private fun getIndex(size: Int, index: Int) =   if      (index < 0     ) size-1
                                                        else if (index > size-1) 0
                                                        else                     index
    }
}