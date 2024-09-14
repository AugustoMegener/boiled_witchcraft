package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister


abstract class Register<T>(key: ResourceKey<Registry<T>>, id: String = ID) {

    @Suppress("UNCHECKED_CAST")
    constructor(registry: Registry<T>, id: String = ID): this(registry.key() as ResourceKey<Registry<T>>, id)

    val register = DeferredRegister.create(key, id)

    infix fun <R : T> String.by(builder: () -> R): DeferredHolder<T, R> = register.register(this, builder)


}