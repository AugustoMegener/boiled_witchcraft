package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistry {
    val itemRegistry: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val pencilItem: DeferredItem<Item> = itemRegistry.registerItem("pencil")
                                                    { Item(Item.Properties().durability(100)) }
}