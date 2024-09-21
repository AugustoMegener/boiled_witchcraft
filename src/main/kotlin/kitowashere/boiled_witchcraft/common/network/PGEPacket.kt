package kitowashere.boiled_witchcraft.common.network

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.capabilities.Caps.Entity.glyphEditor
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation.parse
import net.neoforged.neoforge.network.handling.IPayloadContext

data class PGEPacket(val way: WrapWay, val editing: Boolean) : CustomPacketPayload {

    companion object : PayloadHandler<PGEPacket>() {

        override val type = CustomPacketPayload.Type<PGEPacket>(parse(("$ID:player_glyph_edit")))

        override val codec: StreamCodec<RegistryFriendlyByteBuf, PGEPacket> = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, { it.way.toString() },
            ByteBufCodecs.BOOL, { it.editing },
            { w, e -> PGEPacket(WrapWay.valueOf(w), e) }
        )

        override fun handle(packet: PGEPacket, ctx: IPayloadContext) {
            ctx.player().glyphEditor.editor.run { if (packet.editing) value::wrap else ::wrap } (packet.way)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = type
}