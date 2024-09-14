package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping.keyMappings
import kitowashere.boiled_witchcraft.client.keymapping.WrapKeys
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas
import kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator.GlyphItemDecorator
import kitowashere.boiled_witchcraft.client.render.gui.overlay.GlyphEditorOverlay
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent
import net.minecraft.resources.ResourceLocation.parse as loc


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ID, value = [Dist.CLIENT])
object EventBusClient {

//    @SubscribeEvent
//    fun onRegisterClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
//        event.register(GlyphStackTooltip::class.java) { ClientGlyphStackTooltip(it) }
//    }

    @SubscribeEvent
    fun onRegisterItemDecorations(event: RegisterItemDecorationsEvent) {
        BuiltInRegistries.ITEM.forEach { event.register(it, GlyphItemDecorator()) }
    }

    @SubscribeEvent
    fun onRegisterKeyMappings(event: RegisterKeyMappingsEvent) {
        listOf(WrapKeys).forEach { it.keyBuilder(Keymapping) }
        keyMappings.forEach { event.register(it) }
    }

    @SubscribeEvent
    fun onRegisterClientReloadListeners(event: RegisterClientReloadListenersEvent) {
        event.registerReloadListener(GlyphAtlas)
    }

    @SubscribeEvent
    fun onRegisterGuiLayers(event: RegisterGuiLayersEvent) {
        event.registerAboveAll(loc("$ID:glyph_editor"), GlyphEditorOverlay)

    }
}