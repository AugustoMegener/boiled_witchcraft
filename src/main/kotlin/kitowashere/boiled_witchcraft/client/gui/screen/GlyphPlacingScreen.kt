package kitowashere.boiled_witchcraft.client.gui.screen

import io.kito.kore.util.minecraft.localPlayer
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.client.render.glyph.GuiGlyphRender
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component.literal
import org.joml.Vector2i

object GlyphPlacingScreen : Screen(literal("")) {

    private val backgroundLocation = local("glyph_editing_background")
    private val placePosLocation = local("placeable_pos")

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)

        val glyph = localPlayer!!.glyphEditor.stack
        val glyphSize = 16
        val glyphRender = GuiGlyphRender(glyph, glyphSize)

        val bgWidth = glyphRender.widthInPixels + 16
        val bgHeight = glyphRender.heightInPixels + 16

        val bgX = (guiGraphics.guiWidth() - bgWidth) / 2
        val bgY = (guiGraphics.guiHeight() - bgHeight) / 2

        val glyphX = bgX + 8
        val glyphY = bgY + 8

        guiGraphics.blitSprite(backgroundLocation, bgX, bgY, bgWidth, bgHeight)
        glyphRender.render(guiGraphics, glyphX, glyphY)

        repeat(glyph.size) { x ->
            repeat(glyph.size) { y ->
                if (glyph.isValidPos(Vector2i(x, y)))
                    guiGraphics.blitSprite(
                        placePosLocation, glyphX + x * glyphSize, glyphY + y * glyphSize, glyphSize, glyphSize)
            }
        }
    }
}