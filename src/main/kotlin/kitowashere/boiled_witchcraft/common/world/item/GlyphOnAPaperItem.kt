package kitowashere.boiled_witchcraft.common.world.item

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.fromID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.getID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphTooltip
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*

class GlyphOnAPaperItem(properties: Properties) : Item(properties) {

    override fun verifyTagAfterLoad(pTag: CompoundTag) {
        super.verifyTagAfterLoad(pTag)
    }

    override fun getTooltipImage(pStack: ItemStack): Optional<TooltipComponent> =
        Optional.of(GlyphTooltip(getGlyphType(pStack), getGlyphData(pStack)))

    private fun getGlyphType(stack: ItemStack) = fromID(
        (stack.tag.also { if (it==null) setGlyphType(stack, GlyphType.placeholder) })!!
            .getString("glyph_type")
    )!!

    private fun getGlyphData(stack: ItemStack) = getGlyphType(stack).newData().also { data ->
        data.deserializeNBT((stack.tag.also { if (it==null) setGlyphData(stack, data) })!!
            .getCompound("glyph_type"))
    }

    private fun setGlyphType(stack: ItemStack, glyphType: GlyphType<*>) {
        (stack.tag ?: (CompoundTag().also { stack.tag = it })).putString("glyph_type", getID(glyphType))
    }

    private fun setGlyphData(stack: ItemStack, glyphData: GlyphData) {
        (stack.tag ?: (CompoundTag().also { stack.tag = it })).put("glyph_data", glyphData.serializeNBT())
    }
}