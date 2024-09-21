package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

class IntField(location: ResourceLocation,
               name: String,
               value: Int,
               private  val     maxRange : (   ) -> Int,
               private  val  valueGetter : (Int) -> Int,
               private  val  indexGetter : (Int) -> Int,
               private  val displayValue : (Int) -> String = { "$it" })
    : DataField<Int>(location, name, value)
{
    override val valueComponent: MutableComponent get() = Component.literal(displayValue(value))

    override fun Int.asIndex() = indexGetter(this)

    override fun valueFromIndex(i: Int) = valueGetter(i)

    override val maxIndex get() = maxRange()

    /*override fun serializeNBT(provider: HolderLookup.Provider): IntTag = IntTag.valueOf(value)
    override fun deserializeNBT(provider: HolderLookup.Provider, nbt: IntTag) { value = nbt.asInt }*/
}