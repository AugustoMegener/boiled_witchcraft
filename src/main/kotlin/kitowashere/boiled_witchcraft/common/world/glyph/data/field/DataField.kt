package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.common.core.Select
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import kotlin.math.min
import kotlin.reflect.KProperty

abstract class DataField<T>(val location: ResourceLocation, val name: String) : Select<T>() {

    operator fun <C> getValue(cls: C, property: KProperty<*>) = value
    operator fun <C> setValue(cls: C, property: KProperty<*>, v: T) { wrappedIndex = min(0, indexOf(v)) }


    val nameComponent = Component.translatable(location.toLanguageKey())
    abstract val valueComponent: MutableComponent
}