package kitowashere.boiled_witchcraft.common.resource.mohs

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.common.resource.canvas.ItemGlyphCanvas
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

object MohsManager : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().create(), "mohs") {
    override fun apply(obj:             MutableMap<ResourceLocation, JsonElement>,
                       resourceManager: ResourceManager,
                       profiler:        ProfilerFiller)
    {
        ItemGlyphCanvas.clear()

        for (i in obj) {
            val location = i.key
            val res = location.let { ResourceLocation(it.namespace, it.namespace.split("/").last()) }
            val json = i.value

            try {
                when (location.path.split("/")[1]) {
                    "item"  -> ItemGlyphCanvas::register
                    "block" -> TODO("Make block canvas")
                    else    -> throw IllegalArgumentException()
                } (res, json.asJsonObject)
            } catch (exc: JsonParseException)       { debugSkipping(location, exc) }
              catch (exc: IllegalArgumentException) { debugSkipping(location, exc) }
        }
    }

    private fun debugSkipping(resourceLocation: ResourceLocation, exc: RuntimeException) {
        BoiledWitchcraft.logger.debug(
            "Skipping loading mohs scale $resourceLocation as it's conditions were not met", exc
        )
    }
}