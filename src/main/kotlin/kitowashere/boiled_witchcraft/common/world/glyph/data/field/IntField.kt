package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import net.minecraft.nbt.IntTag

class IntField(name: String, value: Int) : DataField<Int, IntTag>(name, value) {
    override fun serializeNBT(): IntTag = IntTag.valueOf(value)

    override fun deserializeNBT(key: IntTag) { value = key.asInt }
}