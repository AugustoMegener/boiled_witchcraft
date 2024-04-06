package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip.ClientGlyphStackTooltip
import kitowashere.boiled_witchcraft.common.inventory.tooltip.GlyphStackTooltip
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object EventBusClient {

    @SubscribeEvent
    fun onRegisterClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
        event.register(GlyphStackTooltip::class.java) { ClientGlyphStackTooltip(it) }
    }
}