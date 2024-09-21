package kitowashere.boiled_witchcraft.common.util

import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

object KotlinUtil {

    operator fun <T, T1: T, T2: T> Pair<T1, T2>.get(i: Int): T =
        when (i) { 0 -> first; 1 -> second; else -> throw ValueException("Only 0 or 1 are accepted") }

    class Delegation<T>(private val getter: () -> T, private val setter: (T) -> Unit) {

        constructor(field: KMutableProperty<T>) : this(field.getter::call, { field.setter.call(it) })

        operator fun getValue(cls: Any, property: KProperty<*>) = getter()
        operator fun setValue(cls: Any, property: KProperty<*>, value: T) { setter(value) }
    }
}


