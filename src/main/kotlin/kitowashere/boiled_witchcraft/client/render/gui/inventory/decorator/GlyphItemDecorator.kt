package kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.glyphCanvas
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.glyphStack


import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.IItemDecorator

class GlyphItemDecorator : IItemDecorator {
    private var shouldRender = HashMap<ItemStack, Boolean>()

    override fun render(guiGraphics: GuiGraphics, font: Font, stack: ItemStack, xOffset: Int, yOffset: Int): Boolean {
        if (shouldRender[stack] == true) {
            guiGraphics.pose().translate(0f, 0f, 199f)
            guiGraphics.blit(texture, xOffset, yOffset, 0F, 0F, 16, 16, 16, 16)
        }

        (stack.glyphCanvas?.let    { stack.glyphStack?.isEmpty?.let { !it } ?: false  } ?: false)
                           .takeIf { it != shouldRender[stack]                        }
                          ?.let    { shouldRender[stack] = it; return true            }
        return false
    }

    companion object { val texture = ResourceLocation(ID, "textures/item/decorator/glyphed.png") }
}