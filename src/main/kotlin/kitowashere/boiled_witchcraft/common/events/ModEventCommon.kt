package kitowashere.boiled_witchcraft.common.events

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ID)
object ModEventCommon {

    @SubscribeEvent
    fun onRegisterPackets(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar(ID).optional()

        /*listOf(

        ).forEach { registrar.playToClient(it.type, it.codec, it) }*/
    }

    @SubscribeEvent
    fun onAttachCap(event: RegisterCapabilitiesEvent) {
        // Entities
    }
}