package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kitowashere.boiled_witchcraft.BoiledWitchcraft.logger
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

abstract class ResourceRegistry<T, R>(private val registry: Registry<T>, protected val dir: String)
             : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().create(), dir)
{
    val resources = HashMap<T, R>()

    fun clear() { resources.clear() }

    protected abstract fun makeRegistry(json: JsonElement): R

    override fun apply(objs: MutableMap<ResourceLocation, JsonElement>, p1: ResourceManager, p2: ProfilerFiller) {
        for (i in objs) {
            val location = i.key

            try { resources[registry[location]!!] = makeRegistry(i.value) }

            catch (exc: JsonParseException)       { debugSkipping(location, exc) }
            catch (exc: IllegalArgumentException) { debugSkipping(location, exc) }
            catch (exc: NullPointerException)     { debugSkipping(location, exc) }
        }
    }

    private fun debugSkipping(resourceLocation: ResourceLocation, exc: RuntimeException) {
        logger.warn("Skipping loading canvas $resourceLocation as it's conditions were not met", exc)
    }
}