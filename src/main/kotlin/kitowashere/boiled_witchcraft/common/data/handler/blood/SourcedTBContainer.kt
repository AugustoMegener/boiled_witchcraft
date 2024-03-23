package kitowashere.boiled_witchcraft.common.data.handler.blood

open class SourcedTBContainer : ArrayList<() -> ITitanBloodHandler>(), ITitanBloodHandler {
    override fun get(): Int = sumOf { it.invoke().get() }

    override fun use(value: Int): Int {
        var toUse = value
        var blood = 0

        forEach {
            if (blood < value) {
                val used = it.invoke().use(value)

                toUse -= used
                blood += used
            }
        }

        return  blood
    }

    override fun absorb(other: ITitanBloodHandler) { add { other } }
}