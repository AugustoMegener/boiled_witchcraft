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

    val composing = glyphTooltip.composing
    val coreGlyph = composing.coreGlyph

    val renderText get() = coreGlyph.type != null && composing.glyphCount <= 0

    override fun getHeight()            = BG_SIZE + 6 + if (renderText) 10 else 0
    override fun getWidth(pFont: Font)  = BG_SIZE

    override fun renderText(pFont: Font, pMouseX: Int, pMouseY: Int, pMatrix: Matrix4f,
                            pBufferSource: MultiBufferSource.BufferSource)

    {
        if (renderText) {
            pFont.drawInBatch(translatableName(coreGlyph.type!!), pMouseX.toFloat(), pMouseY.toFloat(), -1,
                              true, pMatrix, pBufferSource, Font.DisplayMode.NORMAL, 0,
                              15728880)
        }

    }

    override fun renderImage(pFont: Font, pX: Int, pY: Int, pGuiGraphics: GuiGraphics) {
        val y = pY + if (renderText) 10 else 0

        pGuiGraphics.blit(bgTexture, pX, y, 0f, 0f,
                          64, 64,64, 64)

        val size = composing.size * 16

        if (coreGlyph.type != null) {
            pGuiGraphics.blit(getGlyphTexture(coreGlyph.type), pX + ((BG_SIZE - size) / 2),
                                              y + ((BG_SIZE - size) / 2), 0f, 0f,
                                              size, size, size, size)
        }

        for (i in composing.getGlyphs().filter { it.value.type != null }) {
            val xx = i.key.x
            val yy = i.key.y

            val glyph = i.value
            val size2 = glyph.data.size

            pGuiGraphics.blit(getGlyphTexture(glyph.type!!), pX + xx * 16, y + yy * 16,
                             0f, 0f, size2, size2, size2, size2)
        }
    }

    companion object {
        val bgTexture = ResourceLocation(ID,"textures/gui/paper_background.png")

        const val BG_SIZE = 64
    }
}