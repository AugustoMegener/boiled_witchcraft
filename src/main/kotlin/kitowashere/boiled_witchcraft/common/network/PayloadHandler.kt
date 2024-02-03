package kitowashere.boiled_witchcraft.common.network

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.network.PayloadHandler.ExamplePacket
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component.translatable
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.PlayPayloadContext
import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * Class that helps to deal with custom packets, this class hybridizes the data class and the handler class,
 * the class must extend [CustomPacketPayload] and the companion object this class.
 * @param T The type of packet that will be handled.
 * @param packet The class of packet that will be handled.
 * @param packetId The ID of the packet.
 * @sample ExamplePacket Sample of how to use it
 */
abstract class PayloadHandler<T: CustomPacketPayload>(
    private val packet: KClass<T>,
    val packetId: ResourceLocation
) {

    private lateinit var clientPacketEvent: PacketEvent<T>.() -> Unit
    private lateinit var serverPacketEvent: PacketEvent<T>.() -> Unit


    fun handleDataClient(data: CustomPacketPayload, context: PlayPayloadContext) {
        handleData(packet.cast(data), context, clientPacketEvent)
    }

    fun handleDataServer(data: CustomPacketPayload, context: PlayPayloadContext) {
        handleData(packet.cast(data), context, serverPacketEvent)
    }

    private fun handleData(data: T, context: PlayPayloadContext, action: PacketEvent<T>.() -> Unit) {
        val packet = PacketEvent(data)
        action.invoke(packet)

        packet.network.invoke()

        context.workHandler().submitAsync {
            packet.main.invoke()
        }.exceptionally { e: Throwable ->
            context.packetHandler().disconnect(translatable(ID + "networking.failed", e.message))
            null
        }
    }

    /**
     * Defines the event that will occur when the packet is sent to the client.
     * @param packetEvent The builder of the event.
     * @sample clientEventSample a sample of how to use [clientEvent]
     * @see packetEvent
     */
    fun clientEvent(packetEvent: PacketEvent<T>.() -> Unit) {
        clientPacketEvent = packetEvent;
    }

    /**
     * Defines the event that will occur when the packet is sent to the client.
     * @param packetEvent The builder of the event.
     * @sample serverEventSample a sample of how to use [serverEvent]
     * @see PacketEvent
     */
    fun serverEvent(packetEvent: PacketEvent<T>.() -> Unit) {
        serverPacketEvent = packetEvent
    }

    abstract fun fromBytes(buf: FriendlyByteBuf): T

    /**
     * A class used to define packet events. Events are divided into network thread and main thread.
     * Data processing must be done on the network thread, the main thread must use the data in the dist it is working on.
     * @param T The type of packet that will be handled
     * @param data The packet that the events will get the data.
     * @see <a href="https://docs.neoforged.net/docs/networking/payload/">Registering Payloads<a>
     */
    class PacketEvent<T: CustomPacketPayload>(val data: T) {

        lateinit var network: () -> Unit
        lateinit var main: () -> Unit

        /**
         * The function to define the network thread event.
         * @sample networkThreadSample How should [networkThread] be used:
         * @see clientEvent
         * @see serverEvent
         */
        fun networkThread(event: (T) -> Unit) { network = { event.invoke(data) } }

        /**
         * The function to define the network thread event.
         * @sample mainThreadSample How should [mainThread] be used:
         * @see clientEvent
         * @see serverEvent
         */
        fun mainThread(event: (T) -> Unit) { main = { event.invoke(data) } }
    }

    /**
     * Examples. Please ignore.
     */
private fun networkThreadSample() {
    // Note: clientEvent run on client, clientEvent run on server.
    clientEvent {
        networkThread {
            // The code that will run on network thread.
            // it: the packet handled
        }
    }
}
private fun mainThreadSample() {
    // Note: clientEvent run on client, clientEvent run on server.
    clientEvent {
        networkThread {
            // The code that will run on main thread.
            // it: the packet handled.
        }
    }
}
private fun clientEventSample() {
    clientEvent {
        networkThread {// Only add it if you need it.
            // The code that will run on network thread.
            // it: the packet handled.
        }
        mainThread {
            // The code that will run on main thread.
            // it: the packet handled.
        }
    }
}
private fun serverEventSample() {
    serverEvent {
        networkThread {// Only add it if you need it.
            // The code that will run on network thread.
            // it: the packet handled.
        }
        mainThread {
            // The code that will run on main thread.
            // it: the packet handled.
        }
    }
}
class ExamplePacket(/*packet data*/) : CustomPacketPayload {
    companion object : PayloadHandler<ExamplePacket>(
        ExamplePacket::class, ResourceLocation("id", "name")
    ) {
        init {
            // Only add if it will be sent to the client side
            clientEvent {
                networkThread {// Only add it if you need it.
                    // The code that will run on network thread.
                    // it: the packet handled.
                }
                mainThread {
                    // The code that will run on main thread.
                    // it: the packet handled.
                }
            }

            // Only add if it will be sent to the server side
            serverEvent {
                networkThread {// Only add it if you need it.
                    // The code that will run on network thread.
                    // it: the packet handled.
                }
                mainThread {
                    // The code that will run on main thread.
                    // it: the packet handled.
                }
            }
        }

        override fun fromBytes(buf: FriendlyByteBuf): ExamplePacket
        {
            return ExamplePacket() // Decoded packet
        }
    }

    override fun write(pBuffer: FriendlyByteBuf)
    {
        // Encode the packet data
    }

    override fun id(): ResourceLocation
    {
        return packetId
    }
}
}