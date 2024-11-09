package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.world.inventory.menu.GlyphDeskMenu
import net.minecraft.core.registries.BuiltInRegistries.MENU
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object MenuTypeRegistry : Register<MenuType<*>>(MENU) {
    val glyphDeskMenuType: MenuType<GlyphDeskMenu> by "glyph_desk" by { IMenuTypeExtension.create(::GlyphDeskMenu) }
}