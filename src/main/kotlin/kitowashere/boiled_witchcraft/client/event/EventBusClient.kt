package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.gui.screens.inventory.GlyphDeskScreen
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping.keyMappings
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas
import kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator.GlyphItemDecorator
import kitowashere.boiled_witchcraft.client.render.gui.overlay.ItemGlyphDisplayOverlay
import kitowashere.boiled_witchcraft.common.registry.MenuTypeRegistry.glyphDeskMenuType
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.*
import net.minecraft.resources.ResourceLocation.parse as loc


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ID, value = [Dist.CLIENT])
object EventBusClient {

//    @SubscribeEvent
//    fun onRegisterClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
//        event.register(GlyphStackTooltip::class.java) { ClientGlyphStackTooltip(it) }
//    }

    @SubscribeEvent
    fun RegisterMenuScreensEvent.onRegisterMenuScreen() {
        register(glyphDeskMenuType, ::GlyphDeskScreen)
    }

    @SubscribeEvent
    fun RegisterItemDecorationsEvent.onRegisterItemDecorations() {
        BuiltInRegistries.ITEM.forEach { register(it, GlyphItemDecorator) }
    }

    @SubscribeEvent
    fun RegisterKeyMappingsEvent.onRegisterKeyMappings() {
        /*listOf().forEach { it.keyBuilder(Keymapping) }*/
        keyMappings.forEach { register(it) }
    }

    @SubscribeEvent
    fun RegisterClientReloadListenersEvent.onRegisterClientReloadListeners() {
        registerReloadListener(GlyphAtlas)
    }

    @SubscribeEvent
    fun RegisterGuiLayersEvent.onRegisterGuiLayers() {
        registerAboveAll(loc("$ID:item_glyph_display"), ItemGlyphDisplayOverlay)
    }
}