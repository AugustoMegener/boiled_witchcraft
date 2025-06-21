package kitowashere.boiled_witchcraft.client.render.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import net.minecraft.client.gui.GuiGraphics

class GuiGlyphRender(glyphStack: GlyphStack, val glyphSize: Int) : GlyphRenderBase() {

    val  widthInPixels get() =  width * glyphSize
    val heightInPixels get() = height * glyphSize

    init { super.glyphStack = glyphStack }


    fun render(guiGraphics: GuiGraphics, x: Int, y: Int)
    {
        sprites.forEach {
            val size = it.size * glyphSize

            guiGraphics.blit(x + it.pos.x, y + it.pos.y, 1, size, size, it.sprite)
        }
    }
}