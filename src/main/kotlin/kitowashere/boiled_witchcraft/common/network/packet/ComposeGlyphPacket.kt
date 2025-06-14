package kitowashere.boiled_witchcraft.common.network.packet

import io.kito.kore.common.data.codec.stream.Send
import io.kito.kore.common.network.Packet
import io.kito.kore.common.network.PacketTarget
import io.kito.kore.common.network.PacketType
import io.kito.kore.common.network.RegisterPacket
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.common.network.VERSION
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.GlyphToEditKind
import net.neoforged.neoforge.network.handling.IPayloadContext

class ComposeGlyphPacket : Packet(ComposeGlyphPacket) {

    @Send
    val data = false

    override fun invoke(ctx: IPayloadContext?) {
        val editor = (ctx?.player() ?: minecraftClient.player!!).glyphEditor

        ctx.main {
            if (editor.options.glyphToEditKind == GlyphToEditKind.SOURCE) editor.compose()
        }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<ComposeGlyphPacket>(local("compose_glyph"), ComposeGlyphPacket::class)
}