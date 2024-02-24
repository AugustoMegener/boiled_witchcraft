package kitowashere.boiled_witchcraft.common.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.renderer.entity.block.GlyphBlockRenderer
import kitowashere.boiled_witchcraft.common.data.Caps
import kitowashere.boiled_witchcraft.common.data.handler.blood.EntityTBContainer
import kitowashere.boiled_witchcraft.common.network.ChunkBloodPacket
import kitowashere.boiled_witchcraft.common.network.PayloadHandler
import kitowashere.boiled_witchcraft.common.registry.BlockEntityRegistry
import net.minecraft.world.entity.EntityType
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent
import org.apache.logging.log4j.Level


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID)
object ModEventCommon {

    @SubscribeEvent
    fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.glyphBlockEntity.get()) { GlyphBlockRenderer(it) }
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        BoiledWitchcraft.LOGGER.log(Level.INFO, "Hello! This is working!")
    }

    /**
     * @see PayloadHandler
     */
    @SubscribeEvent
    fun onRegisterPackets(event: RegisterPayloadHandlerEvent) {
        val registrar = event.registrar(ID)

        listOf(
            ChunkBloodPacket
        ).forEach {
            registrar.play(it.packetId, it::fromBytes) { h->h
                .client(it::handleDataClient)
                .server(it::handleDataServer)
            }
        }
    }

    @SubscribeEvent
    fun onAttachCap(event: RegisterCapabilitiesEvent) {
        event.registerEntity(Caps.TitanBlood.entity, EntityType.PLAYER) { entity, _ -> EntityTBContainer(entity) }
    }
}