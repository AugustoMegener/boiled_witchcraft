package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.UNCHECKED_CAST
import io.kito.kore.util.minecraft.literal
import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.heightOption
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.PillarGlyph
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput

class HeightOption : EditorOption<PillarGlyphData, SelectorInput>(SelectorInput::class, heightOption) {

    override val title = "Height".literal

    @Suppress(UNCHECKED_CAST)
    override fun onUsed(
        editor: GlyphEditor,
        glyphAuthor: GlyphAuthor,
        input: SelectorInput,
        data: PillarGlyphData,
        stack: GlyphStack
    ) {
        val glyph = data.type as PillarGlyph<PillarGlyphData>

        data.height = (data.height+input.value)
            .coerceIn(glyph.minHeight(glyphAuthor, data)..glyph.maxHeight(glyphAuthor, data))
    }


}