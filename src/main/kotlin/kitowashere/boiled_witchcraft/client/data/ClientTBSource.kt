package kitowashere.boiled_witchcraft.client.data

import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.totalTitanBloodAmount
import kitowashere.boiled_witchcraft.client.event.mod.ClientTBChangedEvent
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import net.neoforged.neoforge.common.NeoForge
import kotlin.reflect.KProperty

/**
 * Delegates a new titan blood source for [totalTitanBloodAmount].
 *
 * The source value will be part of the sum of [totalTitanBloodAmount],
 * when it is changed, it calls the [ClientTBChangedEvent] event.
 *
 * Note: This class only handles the client side,
 * synchronization with the server and how it will be consumed is up to you.
 *
 */
@OnlyIn(Dist.CLIENT)
class ClientTBSource {

    private var source: Int = 0

    init { sources.add(this) }

    companion object {
        private val sources: ArrayList<ClientTBSource> = ArrayList()
        val all: Int get() = sources.sumOf { it.source }
    }

    operator fun getValue(clientTitanBlood: ClientTitanBlood, property: KProperty<*>): Int = source

    operator fun setValue(clientTitanBlood: ClientTitanBlood, property: KProperty<*>, i: Int) {
        val old = totalTitanBloodAmount
        source = i
        NeoForge.EVENT_BUS.post(ClientTBChangedEvent(old, totalTitanBloodAmount, this))
    }
}