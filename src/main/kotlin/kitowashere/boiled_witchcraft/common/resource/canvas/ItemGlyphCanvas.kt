package kitowashere.boiled_witchcraft.common.resource.canvas

import com.google.gson.JsonElement
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

data class ItemGlyphCanvas(val markers: List<GlyphCanvasMarker>, val size: Int,
                           @OnlyIn(Dist.CLIENT) val texture: ResourceLocation,
                           @OnlyIn(Dist.CLIENT) val textureSize: Int)
{
    companion object : CanvasRegistry<Item, ItemGlyphCanvas>("item") {
        val ItemStack.glyphCanvas get() = resources[this.item]

        var ItemStack.glyphStack: GlyphStack?
            get() = if (glyphCanvas != null) getData(AttachRegistry.glyphStackAttach) else null
            set(value) {
                if (glyphStack == null) return

                setData(AttachRegistry.glyphStackAttach,
                        if (value != null && value.data.size <= glyphCanvas!!.size) value else GlyphStack.empty)
            }

        override fun makeCanvasRegistry(location: ResourceLocation, markers: List<GlyphCanvasMarker>, json: JsonElement)
                     : Pair<Item, ItemGlyphCanvas>
        {
            val texture = json.asJsonObject["texture"]

            return Pair(BuiltInRegistries.ITEM[location],
                        ItemGlyphCanvas(markers, json.asJsonObject["size"].asInt,
                                        texture.asJsonObject["src"].asString
                                                                   .split(":")
                                                                   .dropLastWhile { it.isEmpty() }
                                                                   .run { ResourceLocation(this[0], this[1]) },
                                        texture.asJsonObject["size"].asInt))
        }
    }
}