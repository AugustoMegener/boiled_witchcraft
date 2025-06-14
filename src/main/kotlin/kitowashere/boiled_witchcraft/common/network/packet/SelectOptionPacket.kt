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
import net.neoforged.neoforge.network.handling.IPayloadContext

class SelectOptionPacket(@Send val input: Int) : Packet(SelectOptionPacket) {
    override fun invoke(ctx: IPayloadContext?) {
        val options = (ctx?.player() ?: minecraftClient.player!!).glyphEditor.options

        ctx.main {
            when { input > 0 -> options.next(); input < 0 -> options.prev() }
        }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<SelectOptionPacket>(local("select_option"), SelectOptionPacket::class)
}