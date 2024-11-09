package kitowashere.boiled_witchcraft.client.gui.screens.inventory

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.render.glyph.GuiGlyphRender
import kitowashere.boiled_witchcraft.common.world.inventory.menu.GlyphDeskMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import kotlin.reflect.KProperty
import net.minecraft.resources.ResourceLocation.parse as loc

class GlyphDeskScreen(menu: GlyphDeskMenu, inventory: Inventory, title: Component) :
    AbstractContainerScreen<GlyphDeskMenu>(menu, inventory, title)
{
    private val editingGlyphStackRender = GuiGlyphRender(menu.editingGlyphStack)
    private val createdGlyphStackRender by object {
        var renderer: GuiGlyphRender? = null

        operator fun getValue(obj: Any, property: KProperty<*>) =
            renderer?.takeIf { it.glyphStack == menu.createdGlyphStack } ?:
            menu.createdGlyphStack?.let { GuiGlyphRender(it) }.also { renderer = it }
    }

    init {
        imageHeight = 193
        inventoryLabelY = imageHeight - 96
    }

    override fun renderBg(guiGraphics: GuiGraphics, tick: Float, mouseX: Int, mouseY: Int) {
        guiGraphics.blit(background, leftPos, topPos, 0, 0, imageWidth, imageHeight)
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, tick: Float) {
        super.render(guiGraphics, mouseX, mouseY, tick)

        editingGlyphStackRender .render(guiGraphics, leftPos +  62, topPos + 45)
        createdGlyphStackRender?.render(guiGraphics, leftPos + 121, topPos + 45)

        renderTooltip(guiGraphics, mouseX, mouseY)
    }

    companion object {
        val background: ResourceLocation = loc("$ID:textures/gui/container/glyph_desk.png")
    }
}


