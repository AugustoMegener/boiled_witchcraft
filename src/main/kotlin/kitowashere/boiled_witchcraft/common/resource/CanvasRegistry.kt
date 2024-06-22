package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.JsonElement
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

sealed class CanvasRegistry  <T, R>(registry: Registry<T>, dir: String)
             : ResourceRegistry<T, R>(registry, "canvas/$dir")
{
    final override fun makeRegistry(json: JsonElement): R = makeCanvasRegistry(
            json.asJsonObject["markers"].asJsonArray.map { CanvasMarkerType.valueOf(it.asString.uppercase()) },
            json.asJsonObject)


    abstract fun makeCanvasRegistry(markers: List<CanvasMarkerType>, json: JsonElement): R


    enum class CanvasMarkerType { CANVAS_GRIND, MATERIAL_GRIND }

    object ItemCanvas : CanvasRegistry<Item, ItemCanvas.ItemCanvasData>(BuiltInRegistries.ITEM, "item") {

        val Item.glyphCanvas      get() = resources[this]
        val ItemStack.glyphCanvas get() = item.glyphCanvas

        data class ItemCanvasData(val markers: List<CanvasMarkerType>, val size: Int,
                                  @OnlyIn(Dist.CLIENT) val texture: ResourceLocation,
                                  @OnlyIn(Dist.CLIENT) val textureSize: Int)

        override fun makeCanvasRegistry(markers: List<CanvasMarkerType>, json: JsonElement): ItemCanvasData {
            val obj = json.asJsonObject["item"]

            val texture = obj.asJsonObject["texture"]
            val size = obj.asJsonObject["size"]

            return ItemCanvasData(markers, size.asInt, parse(texture.asJsonObject["src"].asString),
                texture.asJsonObject["size"].asInt)
        }
    }

    object BlockCanvas : CanvasRegistry<Block, List<CanvasMarkerType>>(BuiltInRegistries.BLOCK, "item") {

        val Block.markerType get() = resources[this]
        val BlockState.markerType get() = block.markerType

        override fun makeCanvasRegistry(markers: List<CanvasMarkerType>, json: JsonElement): List<CanvasMarkerType>
            { return markers }
    }

}