package kitowashere.boiled_witchcraft.common.world.glyph.editor.input

import org.joml.Vector2i

sealed interface GlyphPlacementInput : EditorInput {

    @JvmInline
    value class GlyphLinkingInput(val pos: Vector2i) : GlyphPlacementInput

    data object GlyphInscribingInput : GlyphPlacementInput
}