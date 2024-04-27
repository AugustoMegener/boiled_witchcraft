package kitowashere.boiled_witchcraft.common.util.tags

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object ItemTags {
    val glyphEditorTag: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation(ID, "glyph_editor"))
    val glyphCanvasTag: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation(ID, "glyph_canvas"))
}