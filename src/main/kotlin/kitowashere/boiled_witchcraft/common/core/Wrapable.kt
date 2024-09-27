package kitowashere.boiled_witchcraft.common.core

import kitowashere.boiled_witchcraft.common.util.WrapWay
import kitowashere.boiled_witchcraft.common.util.WrapWay.Companion.plus
import kotlin.math.max
import kotlin.math.min

interface Wrapable {
    var wrappedIndex : Int
    val     maxIndex : Int

    fun wrap(way: WrapWay) { wrappedIndex = max(0, min(maxIndex, maxIndex + way)) }
}