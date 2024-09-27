package kitowashere.boiled_witchcraft.common.core

abstract class Select<T> : Wrapable, Iterable<T> {
    override var wrappedIndex = 0

    final override val maxIndex get() = options.size

    val value get() = options[wrappedIndex]

    internal abstract val options: List<T>

    override operator fun iterator() = options.iterator()

    fun indexOf(v: T) = options.indexOf(v)
}