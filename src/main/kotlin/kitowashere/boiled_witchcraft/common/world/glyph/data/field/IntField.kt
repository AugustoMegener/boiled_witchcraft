package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.EditorData
import kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph.GlyphEditorHandler
import kitowashere.boiled_witchcraft.common.util.WrapWay
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

class IntField(name: String,
               value: Int,
               private val validator    : ((Int, WrapWay) -> Int)? = null,
               private val displayValue : (Int) -> String = { "$it" },
               override val renderer: (GlyphEditorHandler.() -> EditorData.SectionRenderer)? = null)
    : DataField<Int>(name, value)
{
    override val valueFromIndex get() = wrappedIndex
    override var value = wrappedIndex

    override val nameComponent: MutableComponent = Component.translatable("field.name.$ID.$name")
    override val valueComponent: MutableComponent get() = Component.literal(displayValue(value))


    /*override fun serializeNBT(provider: HolderLookup.Provider): IntTag = IntTag.valueOf(value)
    override fun deserializeNBT(provider: HolderLookup.Provider, nbt: IntTag) { value = nbt.asInt }*/

    override fun wrap(way: WrapWay) {
        super.wrap(way)
        wrappedIndex = validator?.let { it(wrappedIndex, way) } ?: wrappedIndex
        value = valueFromIndex
    }
}