package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent
import org.apache.logging.log4j.Level


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object EventBusClient {

    @SubscribeEvent
    private fun onClientSetup(event: FMLClientSetupEvent) {
        BoiledWitchcraft.LOGGER.log(Level.INFO, "Initializing client...")
    }

    @SubscribeEvent
    fun onRegisterGuiOverlays(event: RegisterGuiOverlaysEvent) {
        /*listOf<>(

        ).forEach {
            event.registerAboveAll(it.id, it::render)
        }*/
    }
}