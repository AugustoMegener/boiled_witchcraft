package kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.render.glyph.GuiGlyphRender
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.ItemCanvasData
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component

class ClientGlyphStackTooltip(canvas: ItemCanvasData, glyphStack: GlyphStack?) : ClientTooltipComponent {

    private val stack = glyphStack ?: GlyphStack()

    private val size = canvas.size
    private val tooltipSize = size * 16

    private val background = canvas.texture
    private val backgroundSize = canvas.textureSize

    private val sizeLabel = "${size}x$size"
    private val glyphableLabel = Component.translatable("tooltip.$ID.glyphable")

    private val sizeLabelColor = 0x808080

    private val glyphRenderer = GuiGlyphRender(stack)


    override fun getHeight() = if (stack.isEmpty) 20 else tooltipSize + 10
    override fun getWidth(pFont: Font) = if (stack.isEmpty) pFont.width(glyphableLabel) else tooltipSize

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        if (stack.isEmpty) {
            pGuiGraphics.drawString(pFont, glyphableLabel, pX, pY, 0xde9a4b)
            pGuiGraphics.drawString(pFont, sizeLabel, pX , pY + 10, sizeLabelColor)
        } else {
            pGuiGraphics.blit(background, pX, pY, tooltipSize, tooltipSize, 0F, 0F, backgroundSize,
                              backgroundSize, backgroundSize, backgroundSize)

            val margin = ((stack.data.size * 16) + tooltipSize) / 4
            glyphRenderer.render(pGuiGraphics, pX + margin, pY + margin)

            pGuiGraphics.drawCenteredString(pFont, sizeLabel, pX + (backgroundSize + pFont.width(sizeLabel)) / 2,
                                            pY + tooltipSize + 1, sizeLabelColor)
        }
    }
}