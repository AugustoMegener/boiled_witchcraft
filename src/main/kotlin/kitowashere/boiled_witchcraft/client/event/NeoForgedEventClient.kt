package kitowashere.boiled_witchcraft.client.event

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.client.keybinding.Keybinding
import kitowashere.boiled_witchcraft.client.render.gui.inventory.decorator.GlyphItemDecorator
import kitowashere.boiled_witchcraft.common.tags.ItemTags
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.InputEvent
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent
import net.neoforged.neoforge.network.PacketDistributor


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = BoiledWitchcraft.ID, value = [Dist.CLIENT])
object NeoForgedEventClient {

    @SubscribeEvent
    fun onInteractionKeyMappingTriggered(event: InputEvent.InteractionKeyMappingTriggered) {
        val localPlayer = Minecraft.getInstance().player!!

        if (Keybinding.isEnabledInput[event.keyMapping]?.let { it(localPlayer) } == true) {
            Keybinding.clientActionInput[event.keyMapping]?.let { it(localPlayer) }
            Keybinding.syncPacketInput[event.keyMapping]?.let { PacketDistributor.SERVER.noArg().send(it(localPlayer)) }
        }
    }

    @SubscribeEvent
    fun onRegisterItemDecorationsEvent(event: RegisterItemDecorationsEvent) {
        BuiltInRegistries.ITEM.filter { it.defaultInstance.`is`(ItemTags.glyphCanvasTag) }.forEach {
            event.register(it, GlyphItemDecorator())
        }


    }
}