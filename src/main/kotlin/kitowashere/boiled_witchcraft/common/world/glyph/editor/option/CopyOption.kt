package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.minecraft.literal
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.copyOption
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.GlyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.UseInput

class CopyOption : EditorOption<GlyphData, UseInput>(UseInput::class, copyOption) {

    override val title = "Copy".literal

    override fun onUsed(editor: GlyphEditor,
                        glyphAuthor: GlyphAuthor,
                        input: UseInput,
                        data: GlyphData,
                        stack: GlyphStack)
    {
        val foo = editor.stack.copy()
        glyphAuthor.level
        glyphAuthor.clipBoard = foo
    }
}