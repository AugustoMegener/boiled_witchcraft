package kitowashere.boiled_witchcraft.common.util.tags

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object TagItems {
    val glyphEditorTag: TagKey<Item> = ItemTags.create(parse(("$ID:glyph_editor")))
    val glyphCanvasTag: TagKey<Item> = ItemTags.create(parse(("$ID:glyph_canvas")))
}