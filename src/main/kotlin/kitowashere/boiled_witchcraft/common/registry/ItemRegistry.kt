package kitowashere.boiled_witchcraft.common.registry


import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.BlockRegistry.glyphDeskBlock
import net.minecraft.core.registries.BuiltInRegistries.ITEM
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import net.minecraft.resources.ResourceLocation.parse as loc


object ItemRegistry : Register<Item>(ITEM) {
    val glyphEditorTag: TagKey<Item> = ItemTags.create(loc("$ID:glyph_editor"))
    val glyphCanvasTag: TagKey<Item> = ItemTags.create(loc("$ID:glyph_canvas"))

    val         pencilItem by     "pencil" by { Item(Item.Properties().durability(100)) }
    val glyphDeskBlockItem by "glyph_desk" by { BlockItem(glyphDeskBlock, Item.Properties()) }
}