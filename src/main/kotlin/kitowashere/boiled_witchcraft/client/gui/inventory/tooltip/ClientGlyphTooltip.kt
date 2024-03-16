package kitowashere.boiled_witchcraft.client.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.getGlyphTexture
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.translatableName
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphComposingTooltip
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.resources.ResourceLocation
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import org.joml.Matrix4f

@OnlyIn(Dist.CLIENT)
class ClientGlyphTooltip(glyphTooltip: GlyphComposingTooltip) : ClientTooltipComponent {

    private val composing = glyphTooltip.composing
    private val coreGlyphType = composing.coreGlyph.type

    private val renderText get() = coreGlyphType != null && composing.glyphCount <= 0

    override fun getHeight()            = BG_SIZE + 6 + if (renderText) 10 else 0
    override fun getWidth(pFont: Font)  = BG_SIZE

    override fun renderText(pFont: Font, pMouseX: Int, pMouseY: Int, pMatrix: Matrix4f,
                            pBufferSource: MultiBufferSource.BufferSource)
    {
        if (renderText) {
            pFont.drawInBatch(translatableName(coreGlyphType!!), pMouseX.toFloat(), pMouseY.toFloat(), -1,
                              true, pMatrix, pBufferSource, Font.DisplayMode.NORMAL, 0,
                              15728880)
        }
    }

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        val y = pY + if (renderText) 10 else 0

        // Render Background
        pGuiGraphics.blit(bgTexture, pX, y, 0f, 0f,
                          64, 64,64, 64)

        val cSize = composing.size * 16

        val gX = pX + (BG_SIZE - cSize) / 2
        val gY =  y + (BG_SIZE - cSize) / 2
        if (coreGlyphType != null) {
            // Render core glyph
            pGuiGraphics.blit(getGlyphTexture(coreGlyphType), gX, gY, 0f,
                              0f, cSize, cSize, cSize, cSize)
        }

        for (i in composing.getGlyphs().filter { it.value.type != null }) {
            val glyph = i.value
            val gSize = glyph.data.size

            // Render other glyphs
            pGuiGraphics.blit(getGlyphTexture(glyph.type!!), gX + i.key.x * 16, gY + i.key.y * 16,
                             0f, 0f, gSize, gSize, gSize, gSize)
        }
    }

    companion object {
        val bgTexture = ResourceLocation(ID,"textures/gui/paper_background.png")

        const val BG_SIZE = 64
    }
}