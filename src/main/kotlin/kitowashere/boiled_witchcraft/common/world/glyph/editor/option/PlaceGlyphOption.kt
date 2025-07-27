package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.minecraft.literal
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.placeGlyphOption
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.GlyphPlacementInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.GlyphPlacementInput.GlyphInscribingInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.GlyphPlacementInput.GlyphLinkingInput

class PlaceGlyphOption : EditorOption<GlyphData, GlyphPlacementInput>(GlyphPlacementInput::class, placeGlyphOption) {

    override val title = "Place Glyph".literal

    override fun onUsed(editor: GlyphEditor,
                        glyphAuthor: GlyphAuthor,
                        input: GlyphPlacementInput,
                        data: GlyphData,
                        stack: GlyphStack)
    {
        val glyph = glyphAuthor.clipBoard.copy()

        when (input) {
            is GlyphLinkingInput -> stack[input.pos] = glyph
            GlyphInscribingInput -> stack.inscribe(glyph)
        }
    }
}