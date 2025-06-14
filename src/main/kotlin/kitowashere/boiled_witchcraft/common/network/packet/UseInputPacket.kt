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
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.UseInput
import net.neoforged.neoforge.network.handling.IPayloadContext

class UseInputPacket : Packet(UseInputPacket) {

    @Send
    val data = false

    override fun invoke(ctx: IPayloadContext?) {
        ctx.main { (ctx?.player() ?: minecraftClient.player!!).glyphEditor.useOption(UseInput) }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<UseInputPacket>(local("use_input"), UseInputPacket::class)
}