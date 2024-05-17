package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.mojang.serialization.Codec
import com.mojang.serialization.Keyable
import com.mojang.serialization.codecs.RecordCodecBuilder
import kitowashere.boiled_witchcraft.common.util.WrapWay.*
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.IntField
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponentHolder
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.neoforged.neoforge.common.util.INBTSerializable
import kotlin.math.abs

open class GlyphData(val glyph: Glyph) {

    private val fields = ArrayList<DataField<*>>()
    val dataFields get() = fields.toTypedArray()

    var size by IntField("size", 0) {
                                                  n, w -> val s = glyph.sizes
                                                          when(w) { NEXT  -> s.filter { it>=n }.minOrNull() ?: s.max()
                                                                    PRIOR -> s.filter { it<=n }.minOrNull() ?: s.min() }
                                                } .persistent()

    protected fun <T> DataField<T>.persistent() = also { fields.add(it) }

    companion object {
        val codec: Codec<GlyphData> = RecordCodecBuilder.create {
            it.group(
                Glyph.codec.fieldOf("glyph").forGetter(GlyphData::glyph),
                Codec.STRING.listOf().fieldOf("names").forGetter { g -> g.fields.map { f -> f.name } },
                Codec.INT.listOf().fieldOf("indexes").forGetter { g -> g.fields.map { f -> f.wrappedIndex } }
            ).apply(it) { g, n, i ->
                n.zip(i).toMap().run {
                    g.newData().also { d -> d.fields.forEach { f -> this[f.name]?.let { i -> f.wrappedIndex = i } } }
                }
            }
        }
    }
}