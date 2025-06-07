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
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.SelectorInput
import net.neoforged.neoforge.network.handling.IPayloadContext

class SelectorInputPacket(@Send val input: Int) : Packet(SelectorInputPacket) {
    override fun invoke(ctx: IPayloadContext?) {
        val player = (ctx?.player() ?: minecraftClient.player!!)
        val editor = player.glyphEditor

        ctx.main {
            editor.useOption(SelectorInput.of(input))
        }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<SelectorInputPacket>(local("selector_input"), SelectorInputPacket::class)
}