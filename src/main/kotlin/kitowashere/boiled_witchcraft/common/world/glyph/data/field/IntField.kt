package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.nbt.IntTag
import net.minecraft.network.chat.Component

class IntField(name: String, value: Int, val range: IntRange? = null) : DataField<Int, IntTag>(name, value) {
    override var wrappedIndex = 0

    override val nameComponent = Component.translatable("field.name.$ID.$name")
    override val valueComponent get() = nameComponent.append(": $value")

    override fun serializeNBT(): IntTag = IntTag.valueOf(value)
    override fun deserializeNBT(key: IntTag) { value = key.asInt }

    override fun wrap(way: WrapWay) {
        super.wrap(way)
        if (range != null) {
            wrappedIndex = wrappedIndex.coerceIn(range)
        }
        value = wrappedIndex
    }
}