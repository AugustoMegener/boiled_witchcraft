package kitowashere.boiled_witchcraft.server.events

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import net.neoforged.api.distmarker.Dist.DEDICATED_SERVER
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [DEDICATED_SERVER])
object EventBusServer {

    @SubscribeEvent
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        BoiledWitchcraft.LOGGER.log(Level.INFO, "Server starting...")
    }
}