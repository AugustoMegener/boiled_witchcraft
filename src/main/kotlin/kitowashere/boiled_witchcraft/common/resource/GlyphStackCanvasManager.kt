package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kitowashere.boiled_witchcraft.BoiledWitchcraft.logger
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.item.Item
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
                                                         .run { ResourceLocation(this[0], this[1]) } )
            } catch (exc: JsonParseException) { skipDebug(location, exc)
            } catch (exc: IllegalArgumentException) { skipDebug(location, exc) }
        }
    }

    data class GlyphCanvasData(val size: Int, @OnlyIn(Dist.CLIENT) val texture: ResourceLocation)

    val Item.canvasSize     get() = resources[this]?.size

    @get:OnlyIn(Dist.CLIENT)
    val Item.canvasTexture  get() = resources[this]?.texture

    private fun skipDebug(resourceLocation: ResourceLocation, exc: RuntimeException) {
        logger.debug("Skipping loading recipe $resourceLocation as it's conditions were not met", exc)
    }
}