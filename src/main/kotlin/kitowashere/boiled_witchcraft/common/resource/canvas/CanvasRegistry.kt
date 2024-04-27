package kitowashere.boiled_witchcraft.common.resource.canvas

import com.google.gson.JsonElement
import kitowashere.boiled_witchcraft.common.resource.ResourceRegistry
import net.minecraft.resources.ResourceLocation

abstract class CanvasRegistry<T, R> : ResourceRegistry<T, R>("item") {
    final override fun makeRegistry(location: ResourceLocation, json: JsonElement) =
        makeCanvasRegistry(location, json.asJsonObject["markers"].asJsonArray
                                         .map { GlyphCanvasMarker.valueOf(it.asString.uppercase()) },
                           json.asJsonObject[dir])

    abstract fun makeCanvasRegistry(location: ResourceLocation, markers: List<GlyphCanvasMarker>, json: JsonElement)
        : Pair<T, R>
}