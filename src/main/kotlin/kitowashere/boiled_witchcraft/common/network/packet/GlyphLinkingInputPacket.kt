package kitowashere.boiled_witchcraft.common.network.packet

import io.kito.kore.common.data.codec.stream.Send
import io.kito.kore.common.data.codec.stream.StreamCodecSource
import io.kito.kore.common.network.Packet
import io.kito.kore.common.network.PacketTarget
import io.kito.kore.common.network.PacketType
import io.kito.kore.common.network.RegisterPacket
import io.kito.kore.util.minecraft.minecraftClient
import io.netty.buffer.ByteBuf
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.common.network.VERSION
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.GlyphPlacementInput
import net.minecraft.network.codec.StreamCodec
import net.neoforged.neoforge.network.handling.IPayloadContext
import org.joml.Vector2i

class GlyphLinkingInputPacket(@Send val pos: Vector2i) : Packet(GlyphLinkingInputPacket) {

    override fun invoke(ctx: IPayloadContext?) {
        ctx.main {
            (ctx?.player() ?: minecraftClient.player!!).glyphEditor
                .useOption(GlyphPlacementInput.GlyphLinkingInput(pos))
        }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<GlyphLinkingInputPacket>(local("glyph_linking_input"), GlyphLinkingInputPacket::class) {

        private val vector2iStreamCodec: StreamCodec<ByteBuf, Vector2i> =
            StreamCodec.of({ b, v -> b.writeInt(v.x); b.writeInt(v.y) }, { b -> Vector2i(b.readInt(), b.readInt()) })

        @StreamCodecSource
        fun vector2iStreamCodec() = vector2iStreamCodec
    }
}