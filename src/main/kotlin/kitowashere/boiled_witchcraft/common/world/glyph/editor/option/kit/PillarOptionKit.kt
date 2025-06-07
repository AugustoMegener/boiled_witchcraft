package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.debugEndOption
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.heightOption

class PillarOptionKit<T : GlyphData>(glyphToEditKind: GlyphToEditKind) : SimpleOptionKit<T>(glyphToEditKind) {

    override fun initOptions() {
        super.initOptions()

        addOption(heightOption)
        addOption(debugEndOption)
    }
}