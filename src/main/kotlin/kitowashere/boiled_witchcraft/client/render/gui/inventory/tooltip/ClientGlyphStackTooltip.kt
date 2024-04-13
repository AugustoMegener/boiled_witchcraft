package kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.resources.ResourceLocation
import kotlin.math.max

class ClientGlyphStackTooltip(val canvasSize: Int,
                              val canvasTexture: ResourceLocation,
                              val glyphStack: GlyphStack,
                              val other: ClientTooltipComponent? = null) : ClientTooltipComponent
{
    private val size = canvasSize * 16

    override fun getHeight() = if (other != null) size + 9 + other.height else size + 9

    override fun getWidth(pFont: Font) = max(size, other?.getWidth(pFont) ?: 0)
}