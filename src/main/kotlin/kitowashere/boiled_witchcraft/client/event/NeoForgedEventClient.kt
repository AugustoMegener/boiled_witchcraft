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
                for (i in Keymapping.inputsData) {
                    val data = i.value

                    if (i.key.consumeClick() && data.isEnabledInput?.let { it(player) } == true) {
                        data.clientActionInput?.let { it(player) }
                        data.syncPacketInput  ?.let { it(player) }
                    }
                }
            }
        }
    }
}