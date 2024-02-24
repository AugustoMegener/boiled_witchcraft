package kitowashere.boiled_witchcraft.common.data.handler.blood

import kotlin.math.min


interface ITitanBloodHandler {

    /**
     * @return The blood amount.
     */
    fun get(): Int

    /**
     *Subtract blood.
     *
     * It is recommended that you use [useThenDo] or [doThenUse] if possible and use this in pure form as a last resort.
     *
     * @param value the blood amount that will be used.
     * @return How much blood was possible to use.
     * @see useThenDo
     * @see doThenUse
     */
    fun use(value: Int): Int

    /**
     * Absorbs another ITitanBloodHandler into the current one.
     *
     * Removes all the blood from the other and puts it in the current one.
     *
     * @param other The other one.
     */
    fun absorb(other: ITitanBloodHandler)

    /**
     * Takes some of the current blood and creates a new one with it.
     *
     * It only returns if there is enough blood to perform the operation, otherwise it returns null.
     *
     * It works in the form of an infix, it must be used as follows:
     *
     * "amount: [Int] from source: [ITitanBloodHandler]"
     * @param handler The source
     * @return The new container.
     */
    infix fun Int.from(handler: ITitanBloodHandler): ITitanBloodHandler? =
        if (this <= handler.get()) { SimpleTBContainer(this).also { handler.use(this@from) } } else null

    fun useThenDo(amount: Int, action: (ITitanBloodHandler) -> Unit, orElse: () -> Unit = {}) {
        val container = amount from this

        if (container != null) action.invoke(container)
        else orElse.invoke()
    }

    fun doThenUse(amount: Int, action: (Int) -> Boolean) {
        val value = min(get(), amount)
        if (action.invoke(value)) use(value)
    }

}