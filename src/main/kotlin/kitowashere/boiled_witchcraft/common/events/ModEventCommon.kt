package kitowashere.boiled_witchcraft.common.events

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.caps.Caps
import kitowashere.boiled_witchcraft.common.caps.handlers.glyph.PlayerGlyphEditorHandler
import kitowashere.boiled_witchcraft.common.network.PGEPacket
import net.minecraft.world.entity.EntityType
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ID)
object ModEventCommon {

    @SubscribeEvent
    fun onRegisterPackets(event: RegisterPayloadHandlerEvent) {
        val registrar = event.registrar(ID).optional()

        listOf(
            PGEPacket
        ).forEach { registrar.play(it.id, it, it) }
    }

    @SubscribeEvent
    fun onAttachCap(event: RegisterCapabilitiesEvent) {
        // Entities
        event.registerEntity(Caps.Entity.entityGlyphEditor, EntityType.PLAYER) { p,_ -> PlayerGlyphEditorHandler(p) }
    }
}