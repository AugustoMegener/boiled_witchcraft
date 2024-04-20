package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping
import kitowashere.boiled_witchcraft.client.keymapping.Keymapping.keyMappings
import kitowashere.boiled_witchcraft.client.keymapping.WrapKeys
import kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator.GlyphItemDecorator
import kitowashere.boiled_witchcraft.client.render.gui.inventory.tooltip.ClientGlyphStackTooltip
import kitowashere.boiled_witchcraft.common.tags.ItemTags
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphStackTooltip
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object EventBusClient {

    @SubscribeEvent
    fun onRegisterClientTooltipComponentFactories(event: RegisterClientTooltipComponentFactoriesEvent) {
        event.register(GlyphStackTooltip::class.java) { ClientGlyphStackTooltip(it) }
    }

    @SubscribeEvent
    fun onRegisterItemDecorations(event: RegisterItemDecorationsEvent) {
        BuiltInRegistries.ITEM.filter { it.defaultInstance.`is`(ItemTags.glyphCanvasTag) }.forEach {
            event.register(it, GlyphItemDecorator())
        }
    }

    @SubscribeEvent
    fun onRegisterKeyMappings(event: RegisterKeyMappingsEvent) {
        listOf(WrapKeys).forEach { it.keyBuilder(Keymapping) }
        keyMappings.forEach { event.register(it.value) }
    }
}