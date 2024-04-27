package kitowashere.boiled_witchcraft.common.util

interface Wrapable {
    var wrappedIndex: Int

    fun wrap(way: WrapWay) {
        wrappedIndex = wrappedIndex
        wrappedIndex += way.value
        wrappedIndex = wrappedIndex
    }
}