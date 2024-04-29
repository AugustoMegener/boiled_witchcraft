package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.nbt.IntTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

class IntField(name: String, value: Int, private val validator: ((Int, WrapWay) -> Int)? = null)
    : DataField<Int, IntTag>(name, value)
{
    override var wrappedIndex = 0

    override val nameComponent: MutableComponent = Component.translatable("field.name.$ID.$name")
    override val valueComponent: MutableComponent get() = Component.empty().append(nameComponent).append(": $value")

    override fun serializeNBT(): IntTag = IntTag.valueOf(value)
    override fun deserializeNBT(key: IntTag) { value = key.asInt }

    override fun wrap(way: WrapWay) {
        super.wrap(way)
        wrappedIndex = validator?.let { it(wrappedIndex, way) } ?: wrappedIndex

        value = wrappedIndex
    }
}