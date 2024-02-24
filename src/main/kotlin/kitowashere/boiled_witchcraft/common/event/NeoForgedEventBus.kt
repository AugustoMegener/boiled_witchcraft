package kitowashere.boiled_witchcraft.common.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.server.commands.ChunkBloodCommand
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.neoforge.event.RegisterCommandsEvent


@EventBusSubscriber(modid = ID, bus = EventBusSubscriber.Bus.FORGE)
object NeoForgedEventBus {

    @SubscribeEvent
    fun onRegisterCommands(event: RegisterCommandsEvent) {
        listOf(
            ChunkBloodCommand
        ).forEach { it.register(event.dispatcher) }
    }


}