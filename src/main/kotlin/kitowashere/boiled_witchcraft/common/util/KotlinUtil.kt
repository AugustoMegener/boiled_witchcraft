package kitowashere.boiled_witchcraft.common.util

import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException

object KotlinUtil {
    operator fun <T, T1: T, T2: T> Pair<T1, T2>.get(i: Int): T =
        when (i) { 0 -> first; 1 -> second; else -> throw ValueException("Only 0 or 1 are accepted") }
}