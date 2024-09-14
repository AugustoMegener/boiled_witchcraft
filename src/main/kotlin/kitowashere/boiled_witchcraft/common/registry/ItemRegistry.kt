package kitowashere.boiled_witchcraft.common.registry


import net.minecraft.core.registries.BuiltInRegistries.ITEM
import net.minecraft.world.item.Item
import thedarkcolour.kotlinforforge.neoforge.forge.getValue


object ItemRegistry : Register<Item>(ITEM) {
    val pencilItem by "pencil" by { Item(Item.Properties().durability(100)) }
}