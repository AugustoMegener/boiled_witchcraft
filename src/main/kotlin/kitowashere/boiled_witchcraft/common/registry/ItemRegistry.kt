package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.item.GlyphWriterItem
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistry {
    val itemRegistry: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val pencilItem: DeferredItem<GlyphWriterItem> = itemRegistry.registerItem("pencil")
                                                    { GlyphWriterItem(100, it)     }
}