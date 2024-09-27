package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

class IntField(location: ResourceLocation,
               name: String,
               private  val optionsGetter : (   ) -> List<Int>,
               private  val displayValue : (Int) -> String = { "$it" })
    : DataField<Int>(location, name)
{
    override val valueComponent: MutableComponent get() = Component.literal(displayValue(value))

    override val options get() = optionsGetter()
}