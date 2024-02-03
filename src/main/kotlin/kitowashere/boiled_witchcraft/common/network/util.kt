package kitowashere.boiled_witchcraft.common.network

import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.server.ServerLifecycleHooks
import java.util.function.Supplier

/**
 * Sends a packet to all players on a server.
 * @param packetSupplier A supplier that gives the packet that will be sent to all players.
 */
fun runForAllPlayers(packetSupplier: Supplier<CustomPacketPayload>) {
    ServerLifecycleHooks.getCurrentServer().playerList.players.forEach {it.sendPairingData(it) { packetSupplier.get() }}
}