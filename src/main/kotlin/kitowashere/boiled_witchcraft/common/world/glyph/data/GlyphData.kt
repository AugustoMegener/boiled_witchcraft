package kitowashere.boiled_witchcraft.common.world.glyph.data

import kitowashere.boiled_witchcraft.common.util.WrapWay.*
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.IntField
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.neoforged.neoforge.common.util.INBTSerializable
import kotlin.math.abs

open class GlyphData(val glyph: Glyph) : INBTSerializable<CompoundTag> {

    private val fields = ArrayList<DataField<*, in Tag>>()
    val dataFields get() = fields.toTypedArray()

    var size by IntField("size", 0) {
                                                  n, w -> val s = glyph.sizes
                                                          when(w) { NEXT  -> s.filter { it>=n }.minOrNull() ?: s.max()
                                                                    PRIOR -> s.filter { it<=n }.minOrNull() ?: s.min() }
                                                } .persistent()

    @Suppress("UNCHECKED_CAST")
    protected fun <T> DataField<T, *>.persistent() = also { fields.add(it as DataField<T, in Tag>) }

    override fun serializeNBT() = CompoundTag().also { tag -> fields.forEach { tag.put(it.name,
        it.serializeNBT() as Tag) } }

    override fun deserializeNBT(nbt: CompoundTag) {
        nbt.allKeys.withIndex().forEach { fields[it.index].deserializeNBT(nbt.get(it.value)!!) }
    }
}