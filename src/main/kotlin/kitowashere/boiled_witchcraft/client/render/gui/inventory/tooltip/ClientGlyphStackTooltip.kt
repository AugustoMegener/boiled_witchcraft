package kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.common.resource.GlyphStackCanvasManager.canvasSize
import kitowashere.boiled_witchcraft.common.resource.GlyphStackCanvasManager.canvasTexture
import kitowashere.boiled_witchcraft.common.resource.GlyphStackCanvasManager.canvasTextureSize
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphStackTooltip
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent

class ClientGlyphStackTooltip(tooltip: GlyphStackTooltip) : ClientTooltipComponent {

    private val stack = tooltip.stack
    private val background = tooltip.item.canvasTexture!!
    private val backgroundSize = tooltip.item.canvasTextureSize!!

    private val size = tooltip.item.canvasSize!!

    private val tooltipSize = size * 16

    override fun getHeight() = tooltipSize + 10
    override fun getWidth(pFont: Font) = tooltipSize

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        pGuiGraphics.blit(background, pX, pY, 0, 0, backgroundSize, backgroundSize)
        pGuiGraphics.drawCenteredString(pFont, "$size:$size", pX + (backgroundSize / 2), pY + 1, 0)
    }
}