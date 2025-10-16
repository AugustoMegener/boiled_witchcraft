package kitowashere.boiled_witchcraft.client.gui.screen

import io.kito.kore.util.minecraft.localPlayer
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.client.render.glyph.GuiGlyphRender
import kitowashere.boiled_witchcraft.common.network.packet.GlyphLinkingInputPacket
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Renderable
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.narration.NarrationElementOutput
import org.joml.Vector2i
import kotlin.math.ceil

object GlyphPlacingListener : GuiEventListener, NarratableEntry, Renderable {

    private var bgX = 0
    private var bgY = 0

    private val glyphSize = 16

    private val backgroundLocation = local("glyph_editing_background")
    private val placePosLocation = local("placeable_pos")

    override fun setFocused(focused: Boolean) {}

    override fun isFocused() = true

    override fun updateNarration(narrationElementOutput: NarrationElementOutput) {}

    override fun narrationPriority() = NarratableEntry.NarrationPriority.NONE

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        val editor = localPlayer!!.glyphEditor
        val glyph = editor.stack

        val glyphX = bgX + 8
        val glyphY = bgY + 8

        repeat(glyph.size) { x ->
            repeat(glyph.size) { y ->
                val vec = Vector2i(x, y)

                val a = (glyphX + x * glyphSize) + 6..(glyphX + x * glyphSize + glyphSize) - 6
                val b = (glyphY + y * glyphSize) + 6..(glyphY + y * glyphSize + glyphSize) - 6

                if (glyph.isValidPos(vec) &&
                    ceil(mouseX).toInt() in a &&
                    ceil(mouseY).toInt() in b)
                {
                    GlyphLinkingInputPacket(vec).sync()
                    return true
                }
            }
        }

        return false
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        val glyph = localPlayer!!.glyphEditor.stack
        val glyphRender = GuiGlyphRender(glyph, glyphSize)

        val bgWidth = glyphRender.widthInPixels + 16
        val bgHeight = glyphRender.heightInPixels + 16

        bgX = (guiGraphics.guiWidth() - bgWidth) / 2
        bgY = (guiGraphics.guiHeight() - bgHeight) / 2

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