package kitowashere.boiled_witchcraft.common.world.glyph

import com.google.common.collect.ImmutableMap
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException
import kotlin.math.pow
import kotlin.math.sqrt

sealed class GlyphCanvas(core: GlyphCanvas? = null) : INBTSerializable<CompoundTag> {

    protected var children: ImmutableMap<Vector2i, GlyphCanvas?> = ImmutableMap.of()

    class GlyphComposing(core: GlyphCanvas, orbit: Glyph) : GlyphCanvas(core)
    {
        lateinit var orbit: Glyph; private set

        init {
            setOrbit(orbit)
        }

        fun setOrbit(glyph: Glyph) {
            if (!orbit.isStructural) throw ValueException("Orbit glyphs must be structural")

            orbit = glyph

            val diameter = glyph.data.size
            val radius = diameter / 2

            val newChildren = HashMap<Vector2i, GlyphCanvas?>()

            for (x in 0 until diameter) { for (y in 0 until diameter) {
                if (sqrt(((x + 0.5) - radius).pow(2) +
                            ((y + 0.5) - radius).pow(2)) <= radius) { newChildren[Vector2i(x,y)] = null } } }


        }
    }

    class GlyphComponent(private var glyph: Glyph) : GlyphCanvas()

    override fun serializeNBT(): CompoundTag {
        TODO("Not yet implemented")
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        TODO("Not yet implemented")
    }

}