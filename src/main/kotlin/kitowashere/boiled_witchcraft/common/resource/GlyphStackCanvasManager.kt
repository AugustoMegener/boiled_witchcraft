package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kitowashere.boiled_witchcraft.BoiledWitchcraft.logger
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

typealias GSCManager = GlyphStackCanvasManager

object GlyphStackCanvasManager :
    SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().create(), "item/canvas")
{

    private val resources = HashMap<Item, GlyphCanvasData>()

    override fun apply(
        obj: MutableMap<ResourceLocation, JsonElement>,
        resourceMananger: ResourceManager,
        profiler: ProfilerFiller,
    )
    {
        resources.clear()

        for (i in obj) {
            val json = i.value
            val location = i.key

            try {
                resources[BuiltInRegistries.ITEM[location]] = GlyphCanvasData(
                    json.asJsonObject["size"].asInt,
                    json.asJsonObject["texture"].asString.split(":")
                                                         .dropLastWhile { it.isEmpty() }
                                                         .run { ResourceLocation(this[0], this[1]) },
                    json.asJsonObject["textureSize"].asInt
                )
            } catch (exc: JsonParseException) { skipDebug(location, exc)
            } catch (exc: IllegalArgumentException) { skipDebug(location, exc) }
        }
    }

    data class GlyphCanvasData(val size: Int, @OnlyIn(Dist.CLIENT) val texture: ResourceLocation,
                                              @OnlyIn(Dist.CLIENT) val textureSize: Int)

    val ItemStack.glyphCanvas get() = if (this.item in resources) GlyphCanvas(this) else null

    class GlyphCanvas(private val itemStack: ItemStack) {
        val size = resources[itemStack.item]!!.size

        @get:OnlyIn(Dist.CLIENT) val texture     = resources[itemStack.item]!!.texture
        @get:OnlyIn(Dist.CLIENT) val textureSize = resources[itemStack.item]!!.textureSize

        var glyphStack: GlyphStack
            get() = itemStack.getData(AttachRegistry.glyphStackAttach)
            set(value) {
                itemStack.setData(
                    AttachRegistry.glyphStackAttach, if (value.data.size <= size) value else GlyphStack.empty
                )
            }
    }

    private fun skipDebug(resourceLocation: ResourceLocation, exc: RuntimeException) {
        logger.debug("Skipping loading recipe $resourceLocation as it's conditions were not met", exc)
    }
}