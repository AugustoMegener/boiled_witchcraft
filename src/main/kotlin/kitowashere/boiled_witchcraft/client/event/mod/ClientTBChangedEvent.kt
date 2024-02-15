package kitowashere.boiled_witchcraft.client.event.mod

import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.currentChunkTitanBlood
import kitowashere.boiled_witchcraft.client.data.ClientTBSource
import net.neoforged.bus.api.Event
import net.neoforged.fml.LogicalSide
import net.neoforged.neoforge.common.NeoForge

/**
 * Event called when the value of [currentChunkTitanBlood] changes.
 * It must be invoked when one of its sources is changed.
 *
 * This event is fired only on the  [LogicalSide.CLIENT]
 *
 * This event is fired on the [NeoForge.EVENT_BUS].
 *
 * @param newValue The new value after the change.
 */
class ClientTBChangedEvent(
    val oldValue: Int,
    val newValue: Int = ClientTitanBlood.totalTitanBloodAmount,
    val changeSource: ClientTBSource
) : Event()