package kitowashere.boiled_witchcraft

fun Int.coerceInverse(range: IntRange): Int {
    return when {
        this > range.last -> range.first
        this < range.first -> range.last
        else -> this
    }
}