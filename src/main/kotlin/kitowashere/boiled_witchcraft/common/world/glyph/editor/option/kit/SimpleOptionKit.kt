package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.sizeOption

open class SimpleOptionKit<T : GlyphData> : EditorOptionKit<T>() {

    override fun setupOptions() {
        addOption(sizeOption)
    }
}