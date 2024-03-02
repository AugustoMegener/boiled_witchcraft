package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.gui.inventory.tooltip.ClientGlyphTooltip
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphTooltip
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent
import net.neoforged.neoforge.client.event.RenderTooltipEvent
import org.apache.logging.log4j.Level


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object EventBusClient {

    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        BoiledWitchcraft.logger.log(Level.INFO, "Initializing client...")
    }

    @SubscribeEvent
    fun onRegisterClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
        event.register(GlyphTooltip::class.java) { ClientGlyphTooltip(it) }
    }
}