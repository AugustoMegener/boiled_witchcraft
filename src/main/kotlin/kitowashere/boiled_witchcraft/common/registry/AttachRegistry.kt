package kitowashere.boiled_witchcraft.common.registry

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.network.ChunkBloodPacket
import kitowashere.boiled_witchcraft.common.network.runForAllPlayers
import net.minecraft.core.GlobalPos
import net.minecraft.world.level.chunk.LevelChunk
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier


object AttachRegistry {
    val attachTypes: DeferredRegister<AttachmentType<*>> =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ID)

    private val titanBloodAttach: Supplier<AttachmentType<Int>> = attachTypes.register("titan_blood")
    { -> AttachmentType.builder { -> 0 }.serialize(Codec.INT).build() }

    /**
     * A type extension that help to manipulate the titan blood of a [LevelChunk].
     * To store this data, [titanBloodAttach] is used.
     * When set on the server side, a packet is sent to all players that synchronizes the data.
     * @see ChunkBloodPacket
     */
    var LevelChunk.titanBlood: Int
        get() { return getData(titanBloodAttach) }
        set(amount) {
            setData(titanBloodAttach, amount)

            val level = level
            if (!level.isClientSide) {
                runForAllPlayers { ChunkBloodPacket(GlobalPos.of(level.dimension(), pos.worldPosition), titanBlood) }
            }
        }
}