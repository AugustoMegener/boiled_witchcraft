package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.sizeOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser

class SizeOption : EditorOption<GlyphData, SelectorInput>(SelectorInput::class, sizeOption) {

    override fun onUsed(editorUser: EditorUser, input: SelectorInput, data: GlyphData) {
        val sizes = data.type.sizes

        data.size = sizes[sizes.indexOf(data.size)+input.value]
    }
}