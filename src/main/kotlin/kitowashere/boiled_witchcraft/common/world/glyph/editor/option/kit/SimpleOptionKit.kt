package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.sizeOption

open class SimpleOptionKit<T : GlyphData>(glyphToEditKind: GlyphToEditKind) : EditorOptionKit<T>(glyphToEditKind) {

    override fun initOptions() {
        if (glyphToEditKind == GlyphToEditKind.SOURCE) addOption(sizeOption)
    }
}