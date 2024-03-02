package kitowashere.boiled_witchcraft.client.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.getGlyphTexture
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphTooltip
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class ClientGlyphTooltip(glyphTooltip: GlyphTooltip) : ClientTooltipComponent {

    val glyph = glyphTooltip.glyph
    val data = glyphTooltip.glyphData

    override fun getHeight() = 16
    override fun getWidth(pFont: Font) = 16

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        pGuiGraphics.blit(getGlyphTexture(glyph), pX, pY, 0, 0, height, getWidth(pFont))
    }
}