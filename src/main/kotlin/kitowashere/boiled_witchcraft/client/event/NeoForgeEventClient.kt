package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.currentChunkTitanBlood
import kitowashere.boiled_witchcraft.common.network.ChunkBloodPacket
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.titanBlood
import net.minecraft.client.Minecraft
import net.minecraft.core.GlobalPos
import net.minecraft.world.entity.player.Player
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.entity.EntityEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.level.ChunkEvent
import net.neoforged.neoforge.server.ServerLifecycleHooks

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object NeoForgeEventClient {

    @SubscribeEvent
    fun onPlayerLoggedIn(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity

        if (player.level().isClientSide)
            currentChunkTitanBlood = player.level().getChunkAt(player.onPos).titanBlood
    }

    @SubscribeEvent
    private fun onChunkLoad(event: ChunkEvent.Load) {
        if (!event.level.isClientSide) {
            ServerLifecycleHooks.getCurrentServer().playerList.players.forEach {
                val pos = event.chunk.pos.worldPosition
                val level = it.level()

                it.sendPairingData(it) {
                    ChunkBloodPacket(GlobalPos.of(level.dimension(), pos), level.getChunkAt(pos).titanBlood)
                }
            }
        }
    }

    @SubscribeEvent
    fun onEntityEnteringChunk(event: EntityEvent.EnteringSection) {
        val player = event.entity

        if (player.level().isClientSide && event.entity is Player && Minecraft.getInstance().player!!.`is`(event.entity)) {
            currentChunkTitanBlood = player.level().getChunkAt(player.onPos).titanBlood
        }
    }
}