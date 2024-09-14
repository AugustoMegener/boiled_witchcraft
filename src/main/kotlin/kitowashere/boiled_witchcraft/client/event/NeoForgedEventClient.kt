package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping
import net.minecraft.client.Minecraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent


@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = ID, value = [Dist.CLIENT])
object NeoForgedEventClient {

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent.Post) {
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