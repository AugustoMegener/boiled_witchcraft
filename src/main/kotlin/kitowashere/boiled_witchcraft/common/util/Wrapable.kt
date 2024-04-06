package kitowashere.boiled_witchcraft.common.util

interface Wrapable {
    var wrappedIndex: Int

    fun wrap(way: WrapWay) { wrappedIndex += way.value }
}