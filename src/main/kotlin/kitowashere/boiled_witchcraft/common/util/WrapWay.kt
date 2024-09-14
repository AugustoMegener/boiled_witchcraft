package kitowashere.boiled_witchcraft.common.util

enum class WrapWay(val value: Int) {
    NEXT(1), PRIOR(-1);

    val opposite get() = when (this) { NEXT -> PRIOR; PRIOR -> NEXT }
}