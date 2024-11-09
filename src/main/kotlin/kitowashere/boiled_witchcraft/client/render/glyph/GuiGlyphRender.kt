package kitowashere.boiled_witchcraft.client.render.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.gui.GuiGraphics

class GuiGlyphRender(glyphStack: GlyphStack) : GlyphRenderBase() {

    init { super.glyphStack = glyphStack }


    fun render(guiGraphics: GuiGraphics, x: Int, y: Int) {
        sprites.forEach {
            val pos = it.pos
            val size = it.size * 16

            guiGraphics.blit(x + pos.x + size / 4, y + pos.y  + size / 4, 1, size, size, it.sprite)
        }
    }
}