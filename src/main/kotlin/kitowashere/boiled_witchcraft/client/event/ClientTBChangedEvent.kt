package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.currentChunkTitanBlood
import net.neoforged.bus.api.Event
import net.neoforged.neoforge.common.NeoForge

/**
 * Event called when the value of [currentChunkTitanBlood] changes.
 * It must be invoked when one of its sources is changed.
 *
 * This event is fired on the [NeoForge.EVENT_BUS].
 *
 * @param newValue The new value after the change.
 */
 class ClientTBChangedEvent(val newValue: Int = ClientTitanBlood.totalTitanBloodAmount) : Event()