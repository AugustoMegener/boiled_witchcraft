package kitowashere.boiled_witchcraft.common.world.glyph.data

import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.IntField
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.neoforged.neoforge.common.util.INBTSerializable

open class GlyphData() : INBTSerializable<CompoundTag> {

    private val fields = ArrayList<DataField<*, in Tag>>()

    var size by IntField("size", 0).saveData()

    @Suppress("UNCHECKED_CAST")
    protected fun <T> DataField<T, *>.saveData(): DataField<T, *> = also { fields.add(it as DataField<T, in Tag>) }

    override fun serializeNBT() = CompoundTag().also { tag -> fields.forEach { tag.put(it.name,
        it.serializeNBT() as Tag) } }

    override fun deserializeNBT(nbt: CompoundTag) {
        nbt.allKeys.withIndex().forEach { fields[it.index].deserializeNBT(nbt.get(it.value)!!) }
    }
}