package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import net.minecraft.nbt.Tag
import net.neoforged.neoforge.common.util.INBTSerializable
import kotlin.reflect.KProperty

abstract class DataField<T, N : Tag>(val name: String, protected var value: T) : INBTSerializable<N> {
    operator fun getValue(cls: Any, property: KProperty<*>) = value
    operator fun setValue(cls: Any, property: KProperty<*>, v: T) { value = v}
}