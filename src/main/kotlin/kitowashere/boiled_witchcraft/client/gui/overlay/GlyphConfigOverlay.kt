package kitowashere.boiled_witchcraft.client.gui.overlay

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator.GlyphConfigAccess
import kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator.IGlyphConfiguratorHandler
import kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator.PlayerGlyphConfigurator.Companion.glyphConfigurator
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Overlay
import net.minecraft.resources.ResourceLocation
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
object GlyphConfigOverlay : Overlay() {
    val configurator = Minecraft.getInstance().player!!.glyphConfigurator
    val configuratorAccess = GlyphConfigAccess(configurator)

    val categoryIndex get() = configuratorAccess.wrapperIndex

    private val x = Category("a") {
        height {
            0
        }
    }

    override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {

    }

    private class Category(name: String, builder: CategoryBuilder.() -> Unit)
    {
        class CategoryBuilder(val category: Category) {
            fun height(value: (IGlyphConfiguratorHandler) -> Int) {
                category.getHeight = { value(configurator) }
            }
            fun width(value: (IGlyphConfiguratorHandler) -> Int) {
                category.getWith = { value(configurator) }
            }
            fun render(value: (IGlyphConfiguratorHandler, GuiGraphics, Int, Int, Float) -> Unit) {
                category.renderer = { g, x, y, t -> value(configurator, g, x, y, t) }
            }
        }

        val name = ResourceLocation(ID, name).toLanguageKey("overlay.glyph_config.category")
        val index = categories.size

        lateinit var renderer:  (GuiGraphics, Int, Int, Float) -> Unit

        lateinit var getHeight: () -> Int
        lateinit var getWith:   () -> Int

        init {
            builder(CategoryBuilder(this))
            categories.add(this)
        }

        fun render(pGuiGraphics: GuiGraphics, x: Int, y: Int, pPartialTick: Float) {
            pGuiGraphics.drawString(Minecraft.getInstance().font,
                                    (if (index == categoryIndex) "➤ " else "• ") + name,
                                    x, y, 0)

            renderer(pGuiGraphics, x, y, pPartialTick)
        }

        companion object {
            private val categories = ArrayList<Category>()

            private fun getPosOf(index: Int) {
                var x = 0
                var y = 0
                for (i in categories.slice(0..<index)) {

                }
            }
        }
    }
}