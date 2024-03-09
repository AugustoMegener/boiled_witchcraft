package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.item.GlyphOnAPaperItem
import kitowashere.boiled_witchcraft.common.world.item.PencilItem
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistry {
    val itemRegistry: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val pencilItem = itemRegistry.registerItem("pencil") { PencilItem(it.durability(100)) }
    val glyphOnAPaper = itemRegistry.registerItem("glyph_on_a_paper") { GlyphOnAPaperItem(it) }
}
