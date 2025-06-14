package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.minecraft.literal
import kitowashere.boiled_witchcraft.coerceInverse
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.sizeOption
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput

class SizeOption : EditorOption<GlyphData, SelectorInput>(SelectorInput::class, sizeOption) {

    override val title = "Size".literal

    override fun onUsed(
        editor: GlyphEditor,
        editorUser: GlyphAuthor,
        input: SelectorInput,
        data: GlyphData,
        stack: GlyphStack
    ) {
        val sizes = data.type.sizes

        data.size = sizes[(sizes.indexOf(data.size)+input.value).coerceInverse(sizes.indices)]
    }
}