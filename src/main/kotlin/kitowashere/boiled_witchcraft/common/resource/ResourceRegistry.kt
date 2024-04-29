package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

abstract class ResourceRegistry<T, R>(val registry: Registry<T>, protected val dir: String = "")
             : SimpleJsonResourceReloadListener(GsonBuilder().setPrettyPrinting().create(), dir)
{
    protected val resources = HashMap<T, R>()

    fun clear() { resources.clear() }

    protected abstract fun makeRegistry(location: ResourceLocation, json: JsonElement): Pair<T, R>

    fun register(location: ResourceLocation, json: JsonElement) {
        makeRegistry(location, json).let { resources[it.first] = it.second }
    }

    override fun apply(objs: MutableMap<ResourceLocation, JsonElement>, p1: ResourceManager, p2: ProfilerFiller) {
        for (i in obj) {
            val location = i.key
            val res = location.let { ResourceLocation(it.namespace, it.namespace.split("/").last()) }
            val json = i.value

            try {
                res
            } catch (exc: JsonParseException) { debugSkipping(location, exc) }
            catch (exc: IllegalArgumentException) { debugSkipping(location, exc) }
        }
    }
}