package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping
import net.minecraft.client.Minecraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.TickEvent
import net.neoforged.neoforge.event.TickEvent.Phase


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object NeoForgedEventClient {

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == Phase.END) {
            val player = Minecraft.getInstance().player

            if (player != null) {
                for (i in Keymapping.isEnabledInput) {
                    val b = i.value(player)
                    b
                    if (b) {
                        Keymapping.clientActionInput[i.key] ?.let { it(player) }
                        Keymapping.syncPacketInput[i.key]   ?.let { it(player) }
                    }
                }
            }
        }
    }
}