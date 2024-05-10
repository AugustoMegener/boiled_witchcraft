package kitowashere.boiled_witchcraft.common.resource.mosh

import com.google.gson.JsonElement
import kitowashere.boiled_witchcraft.common.resource.ResourceRegistry
import kitowashere.boiled_witchcraft.common.resource.mosh.MohsRegistry.BlockMohs.mohs
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

sealed class MohsRegistry<T>(registry: Registry<T>, dir: String) : ResourceRegistry<T, Float>(registry, "mohs/$dir")
{
    data class MoshData(val value: Float, )

    object ItemMohs : MohsRegistry<Item>(BuiltInRegistries.ITEM, "item") {
        val Item.mohs       get() = if (this is BlockItem) block.mohs else resources[this]
        val ItemStack.mohs  get() = item.mohs
    }

    object BlockMohs : MohsRegistry<Block>(BuiltInRegistries.BLOCK, "block") {
        val Block.mohs      get() = resources[this]
        val BlockState.mohs get() = block.mohs
    }

    override fun makeRegistry(json: JsonElement) = json.asJsonObject["value"].asFloat
}