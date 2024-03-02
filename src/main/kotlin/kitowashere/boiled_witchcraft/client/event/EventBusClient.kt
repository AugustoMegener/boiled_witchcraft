package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.renderer.entity.block.GlyphBlockRenderer
import kitowashere.boiled_witchcraft.common.registry.BlockEntityRegistry.glyphBlockEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import org.apache.logging.log4j.Level


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object EventBusClient {

    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        BoiledWitchcraft.logger.log(Level.INFO, "Initializing client...")
    }

    @SubscribeEvent
    fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(glyphBlockEntity.get()) { GlyphBlockRenderer(it) }
    }
}