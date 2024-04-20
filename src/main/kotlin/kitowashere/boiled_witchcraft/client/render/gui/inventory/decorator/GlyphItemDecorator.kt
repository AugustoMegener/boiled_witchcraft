package kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.resource.GlyphStackCanvasManager.glyphCanvas

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.IItemDecorator

class GlyphItemDecorator : IItemDecorator {
    private var shouldRender = false

    override fun render(guiGraphics: GuiGraphics, font: Font, stack: ItemStack, xOffset: Int, yOffset: Int): Boolean {
        if (shouldRender) { guiGraphics.blit(texture, xOffset, yOffset, 0, 0, 16, 16) }

        (stack.glyphCanvas?.run    { this.glyphStack.isEmpty        } ?: false)
                           .takeIf { it != shouldRender             }
                          ?.let    { shouldRender = it; return true }
        return false
    }

    companion object { val texture = ResourceLocation(ID, "textures/item/decorator/glyphed.png") }
}