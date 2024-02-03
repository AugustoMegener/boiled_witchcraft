package kitowashere.boiled_witchcraft.common.network

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.data.ClientTitanBlood.currentChunkTitanBlood
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.titanBlood
import net.minecraft.client.Minecraft
import net.minecraft.core.GlobalPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

/**
 * Packet that sync the chunk titan blood data.
 * @see PayloadHandler
 */
data class ChunkBloodPacket(val chunkGlobalPos: GlobalPos, val amount: Int) : CustomPacketPayload
{
    companion object : PayloadHandler<ChunkBloodPacket>(
        ChunkBloodPacket::class, ResourceLocation(ID, "chunk_titan_blood")
    ) {
        init {
            clientEvent {
                mainThread {
                    val level = Minecraft.getInstance().level!!

                    if (level.dimension() == data.chunkGlobalPos.dimension()) {
                        val chunk = level.getChunkAt(data.chunkGlobalPos.pos())

                        chunk.titanBlood = data.amount
                        currentChunkTitanBlood = chunk.titanBlood
                    }
                }
            }
        }

        override fun fromBytes(buf: FriendlyByteBuf): ChunkBloodPacket {
            return ChunkBloodPacket(buf.readGlobalPos(), buf.readVarInt())
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeGlobalPos(chunkGlobalPos)
        buf.writeInt(amount)
    }

    override fun id(): ResourceLocation { return packetId }
}