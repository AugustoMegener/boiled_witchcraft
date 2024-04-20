package kitowashere.boiled_witchcraft.common.network

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.caps.Caps.Entity.glyphEditor
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.PlayPayloadContext

class PGEPacket(val way: WrapWay, val editing: Boolean) : CustomPacketPayload {

    override fun write(buf: FriendlyByteBuf) { buf.writeEnum(way) }

    override fun id() = id

    companion object : PlayPayloadHandler<PGEPacket>() {
        override val id = ResourceLocation(ID, "player_glyph_editor_packet")

        override fun apply(buf: FriendlyByteBuf) = PGEPacket(buf.readEnum(WrapWay::class.java), buf.readBoolean())

        override fun handle(packet: PGEPacket, ctx: PlayPayloadContext) {
            ctx.player.ifPresent {
                if (packet.editing) it.glyphEditor.editor.edit(packet.way)
                else                it.glyphEditor.editor.wrap(packet.way)
            }
        }
    }
}