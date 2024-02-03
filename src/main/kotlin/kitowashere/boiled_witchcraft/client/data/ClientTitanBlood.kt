package kitowashere.boiled_witchcraft.client.data

import kitowashere.boiled_witchcraft.client.event.ClientTBChangedEvent
import net.neoforged.neoforge.common.NeoForge.EVENT_BUS

object ClientTitanBlood {
    var currentChunkTitanBlood: Int = 0
        set(value) {
            field = value

            EVENT_BUS.post(ClientTBChangedEvent())
        }

    val totalTitanBloodAmount: Int
        get() = currentChunkTitanBlood // TODO: more blood sources
}