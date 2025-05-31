package kitowashere.boiled_witchcraft.common.network.packet

import io.kito.kore.common.data.codec.stream.Send
import io.kito.kore.common.network.Packet
import io.kito.kore.common.network.PacketTarget
import io.kito.kore.common.network.PacketType
import io.kito.kore.common.network.RegisterPacket
import io.kito.kore.util.minecraft.literal
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.common.network.VERSION
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import net.neoforged.neoforge.network.handling.IPayloadContext

class SelectGlyphPacket(@Send val input: Int) : Packet(SelectGlyphPacket) {
    override fun invoke(ctx: IPayloadContext?) {
        val player = (ctx?.player() ?: minecraftClient.player!!)
        val editor = player.glyphEditor

        ctx.main {
            when { input > 0 -> editor.next(); input < 0 -> editor.prev() }

            if (ctx != null) player.sendSystemMessage("${editor.stack.glyph} ${editor.index}".literal)
        }
    }

    @RegisterPacket(VERSION, PacketTarget.SERVER)
    companion object : PacketType<SelectGlyphPacket>(local("selector_input"), SelectGlyphPacket::class)
}