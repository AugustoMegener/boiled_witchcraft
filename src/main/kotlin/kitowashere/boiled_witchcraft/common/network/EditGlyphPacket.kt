package kitowashere.boiled_witchcraft.common.network

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.editor.PlayerGlyphEditor.Companion.glyphEditor
import kitowashere.boiled_witchcraft.common.data.util.WrapWay
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.PlayPayloadContext

data class EditGlyphPacket(val glyph: Glyph) : CustomPacketPayload {
    enum class EditGlyphMode {
        SELECT, EDIT
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeNbt(glyph.serializeNBT())
    }

    override fun id() = id

    companion object : PlayPayloadHandler<EditGlyphPacket>() {
        override val id = ResourceLocation(ID, "edit_glyph")

        override fun handle(payload: EditGlyphPacket, context: PlayPayloadContext) {
            if (context.level().map { !it.isClientSide }.orElse(false)) {
                context.player.ifPresent { it.glyphEditor.setGlyph(payload.glyph) }
            }
        }

        override fun apply(buf: FriendlyByteBuf) =
            EditGlyphPacket(Glyph.empty.also { it.deserializeNBT(buf.readNbt()!!) })

    }
}