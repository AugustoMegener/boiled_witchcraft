package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.minecraft.literal
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.debugEndOption
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.Inputless

class DebugEndOption : EditorOption<GlyphData, Inputless>(Inputless::class, debugEndOption) {

    override val title = "End".literal

    override fun onUsed(glyphAuthor: GlyphAuthor, input: Inputless, data: GlyphData, stack: GlyphStack) {}
}