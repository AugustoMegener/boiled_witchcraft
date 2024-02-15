package kitowashere.boiled_witchcraft.common.data.handler.blood

import kotlin.math.min

class SimpleTBContainer(private var blood: Int) : ITitanBloodHandler {

    override fun get(): Int = blood

    override fun use(value: Int): Int {
        val old = blood
        blood -= min(blood, value)
        return old - blood
    }

    override fun absorb(other: ITitanBloodHandler) {
        blood += other.get()
        other.use(other.get())
    }

}