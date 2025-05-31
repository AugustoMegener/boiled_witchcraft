package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.heightOption

class PillarOptionKit<T : GlyphData> : SimpleOptionKit<T>() {

    override fun setupOptions() {
        super.setupOptions()

        addOption(heightOption)
    }
}