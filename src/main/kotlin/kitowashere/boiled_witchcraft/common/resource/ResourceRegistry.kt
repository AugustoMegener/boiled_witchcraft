package kitowashere.boiled_witchcraft.common.resource

import com.google.gson.JsonElement
import net.minecraft.resources.ResourceLocation

abstract class ResourceRegistry<T, R>(protected val dir: String = "") {
    protected val resources = HashMap<T, R>()

    fun clear() { resources.clear() }

    protected abstract fun makeRegistry(location: ResourceLocation, json: JsonElement): Pair<T, R>

    fun register(location: ResourceLocation, json: JsonElement) {
        makeRegistry(location, json).let { resources[it.first] = it.second }
    }
}