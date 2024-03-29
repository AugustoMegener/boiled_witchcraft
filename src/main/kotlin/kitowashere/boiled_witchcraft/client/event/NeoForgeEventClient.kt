package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.Keybinding
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.currentChunkTitanBlood
import kitowashere.boiled_witchcraft.common.data.handler.blood.AttachedTBHandler.Companion.titanBlood
import net.minecraft.client.Minecraft
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.InputEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.network.PacketDistributor

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object NeoForgeEventClient {

    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity

        if(player.level().isClientSide)currentChunkTitanBlood = player.level().getChunkAt(player.onPos).titanBlood.get()
    }

    @SubscribeEvent
    fun onInteractionKeyMappingTriggered(event: InputEvent.InteractionKeyMappingTriggered) {
        val localPlayer = Minecraft.getInstance().player!!
        Keybinding.clientActionInput[event.keyMapping]?.let { it(localPlayer) }
        Keybinding.syncPacketInput[event.keyMapping]?.let { PacketDistributor.SERVER.noArg().send(it(localPlayer)) }
    }
}