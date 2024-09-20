package kitowashere.boiled_witchcraft.client.render.gui.overlay

import kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip.ClientGlyphStackTooltip
import kitowashere.boiled_witchcraft.client.util.ClientData.font
import kitowashere.boiled_witchcraft.client.util.ClientData.player
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.glyphCanvas
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.glyphStack
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.LayeredDraw.Layer
import net.minecraft.world.InteractionHand.MAIN_HAND
import net.minecraft.world.InteractionHand.OFF_HAND
import net.minecraft.world.item.ItemStack

object ItemGlyphDisplayOverlay : Layer {

    private val paddingX = 20
    private val paddingY = 20

    private val items get() = listOf(player.getItemInHand(MAIN_HAND), player.getItemInHand(OFF_HAND))

    private val lastItems by lazy { ArrayList(items) }
    private val tooltips  by lazy { ArrayList(items.map { it.tooltip }) }

    override fun render(gui: GuiGraphics, delta: DeltaTracker) {

        repeat(2) { if (lastItems[it] == items[it]) return@repeat
            lastItems[it] = items[it]
            tooltips [it] = items[it].tooltip }


        tooltips[0]?.run {
            renderImage(font, gui.guiWidth() - paddingX - getWidth(font), gui.guiHeight() - paddingY - height, gui) }

        tooltips[1]?.run {
            renderImage(font, paddingX, gui.guiHeight() - paddingY - height, gui) }
    }

    private val ItemStack.tooltip get() =
        glyphCanvas?.let { c ->
        glyphStack ?.let { s -> if (s.isEmpty) null else ClientGlyphStackTooltip(c, s) } }
}