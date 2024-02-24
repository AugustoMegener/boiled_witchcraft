package kitowashere.boiled_witchcraft.common.registry

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.handler.blood.ITitanBloodHandler
import kitowashere.boiled_witchcraft.common.network.ChunkBloodPacket
import kitowashere.boiled_witchcraft.common.network.runForAllPlayers
import net.minecraft.core.GlobalPos
import net.minecraft.world.level.chunk.LevelChunk
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.function.Supplier
import kotlin.math.min


object AttachRegistry {
    val attachRegistry: DeferredRegister<AttachmentType<*>> =
        DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ID)

    private val titanBloodAttach: Supplier<AttachmentType<Int>> = attachRegistry.register("titan_blood")
    { -> AttachmentType.builder { -> 0 }.serialize(Codec.INT).build() }


    /**
     * The chunk's ITitanBloodHandler.
     *
     * This is a type extension that adds an implementation of ITitanBloodHandler using [titanBloodAttach]
     * to store information about the amount of blood available in the chunk.
     * It also sends a packet notifying the client of the data change.
     *
     * @see ITitanBloodHandler
     */
    val LevelChunk.titanBlood get() = object : ITitanBloodHandler {
        override fun get(): Int = getData(titanBloodAttach)

        override fun use(value: Int): Int {
            val old = getData(titanBloodAttach)
            setData(titanBloodAttach, old - min(old, value))
            sync()
            return old - getData(titanBloodAttach)
        }

        override fun absorb(other: ITitanBloodHandler) {
            setData(titanBloodAttach, getData(titanBloodAttach) + other.get())
            other.use(other.get())
            sync()
        }

        fun sync() {
            runForAllPlayers {
                ChunkBloodPacket(GlobalPos.of(level.dimension(), pos.worldPosition), getData(titanBloodAttach))
            }
        }
    }
}