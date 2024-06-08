package kitowashere.boiled_witchcraft.common.world.glyph

import com.mojang.serialization.Codec
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphData
import net.minecraft.world.item.ItemStack
import org.joml.Vector2i

abstract class Glyph(val sizes: Array<Int>) {

    private val location get() = id

    abstract fun newData(): GlyphData

    open fun getSignal(data: GlyphData) = HashMap<Vector2i, Boolean>()
    open fun isHollow(data: GlyphData) = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Glyph) return false

        if (!sizes.contentEquals(other.sizes)) return false
        if (location != other.location) return false

        return true
    }

    override fun hashCode() = 31 * sizes.contentHashCode() + location.hashCode()


    companion object {
        val placeholder = FireGlyph

        val codec: Codec<Glyph> = GlyphRegistry.glyphs.byNameCodec()
    }
}