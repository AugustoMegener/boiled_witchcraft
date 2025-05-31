package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.heightOption
import kitowashere.boiled_witchcraft.common.world.glyph.PillarGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser

class HeightOption : EditorOption<PillarGlyphData, SelectorInput>(SelectorInput::class, heightOption) {

    @Suppress(UNCHECKED_CAST)
    override fun onUsed(editorUser: EditorUser, input: SelectorInput, data: PillarGlyphData) {
        val glyph = data.type as PillarGlyph<PillarGlyphData>

        data.height = (data.height+input.value)
            .coerceIn(glyph.minHeight(editorUser, data)..glyph.maxHeight(editorUser, data))
    }
}