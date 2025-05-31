package kitowashere.boiled_witchcraft.common.world.glyph.editor

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.codec.KCodecSerializer
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.RegisterGlyphEditorOptionKitEvent.Companion.glyphEditorOptionKits
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser

class GlyphEditor(val user: EditorUser) : Selector {

    @Save
    override var index = 0

    override val range get() = 0..user.avaliableGlyphs.size

    @Save
    var stack = GlyphStack.empty
         private set(value) {
             field = value
             options = glyphEditorOptionKits[stack.glyph]!!.supplier()
         }

    var options = glyphEditorOptionKits[stack.glyph]!!.supplier(); private set

    override fun update() {
        stack = user.avaliableGlyphs[index].stack()
    }

    companion object : KCodecSerializer<GlyphEditor>(GlyphEditor::class)
}