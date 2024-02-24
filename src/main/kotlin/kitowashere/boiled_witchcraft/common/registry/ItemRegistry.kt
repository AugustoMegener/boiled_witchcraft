package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.item.PencilItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister

object ItemRegistry {
    val itemRegistry: DeferredRegister.Items = DeferredRegister.createItems(ID)

    val pencilItem = itemRegistry.registerItem("pencil") { PencilItem(Item.Properties()) }
}