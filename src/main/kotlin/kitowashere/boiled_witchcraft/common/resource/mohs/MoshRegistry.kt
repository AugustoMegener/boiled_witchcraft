package kitowashere.boiled_witchcraft.common.resource.mohs

import com.google.gson.JsonElement
import kitowashere.boiled_witchcraft.common.resource.ResourceRegistry
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation

class MoshRegistry<T>(val registry: Registry<T>) : ResourceRegistry<T, Float>(){
    override fun makeRegistry(location: ResourceLocation, json: JsonElement) =
        Pair(registry[location]!!, json.asJsonObject["value"].asFloat)

    companion object {

    }
}