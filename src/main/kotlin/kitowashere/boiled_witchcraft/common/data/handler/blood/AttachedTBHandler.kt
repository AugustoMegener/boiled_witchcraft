package kitowashere.boiled_witchcraft.common.data.handler.blood

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.common.registry.AttachRegistry
import net.minecraft.world.level.chunk.LevelChunk
import net.neoforged.neoforge.attachment.AttachmentType
import net.neoforged.neoforge.attachment.IAttachmentHolder
import kotlin.math.min

open class AttachedTBHandler(private val handler: IAttachmentHolder) : ITitanBloodHandler {

    private val titanBloodAttach = AttachRegistry.attachRegistry.register("titan_blood")
    { -> AttachmentType.builder { -> 0 }.serialize(Codec.INT).build() }

    override fun get(): Int = handler.getData(titanBloodAttach)

    override fun use(value: Int): Int {
        val old = handler.getData(titanBloodAttach)
        handler.setData(titanBloodAttach, old - min(old, value))
        return old - handler.getData(titanBloodAttach)
    }

    override fun absorb(other: ITitanBloodHandler) {
        handler.setData(titanBloodAttach, handler.getData(titanBloodAttach) + other.get())
        other.use(other.get())
    }

    companion object {
        /**
         * The chunk's ITitanBloodHandler.
         *
         * This is a type extension that adds an implementation of ITitanBloodHandler using [titanBloodAttach]
         * to store information about the amount of blood available in the chunk.
         * It also sends a packet notifying the client of the data change.
         *
         * @see ITitanBloodHandler
         */
        val LevelChunk.titanBlood get() = AttachedTBHandler(this)
    }
}