package kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphStackTooltip
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent

class ClientGlyphStackTooltip(tooltip: GlyphStackTooltip) : ClientTooltipComponent {

    private val stack = tooltip.canvas.glyphStack
    private val background = tooltip.canvas.texture
    private val backgroundSize = tooltip.canvas.textureSize

    private val size = tooltip.canvas.size

    private val tooltipSize = size * 16

    override fun getHeight() = tooltipSize + 10
    override fun getWidth(pFont: Font) = tooltipSize

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        pGuiGraphics.blit(background, pX, pY, 0, 0, backgroundSize, backgroundSize)
        pGuiGraphics.drawString(pFont, stack.glyph.translatableName, pX, pY, 0xffffff)
        pGuiGraphics.drawCenteredString(pFont, "${size}x$size", pX + (backgroundSize / 2), pY + 1,
                                        0xffffff)
    }
}