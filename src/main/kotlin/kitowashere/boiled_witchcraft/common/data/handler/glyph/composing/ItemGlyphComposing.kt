package kitowashere.boiled_witchcraft.common.data.handler.glyph.composing

import kitowashere.boiled_witchcraft.common.world.glyph.GlyphComposing
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

class ItemGlyphComposing(private val stack: ItemStack) : IGlyphComposingHandler {

    private var nbt
        get() = (stack.tag ?: CompoundTag().also { stack.tag = it }).getCompound("glyph_composing")
        set(value) { (stack.tag ?: CompoundTag().also { stack.tag = it }).put("glyph_composing", value) }

    private val composing get() = GlyphComposing().also { it.deserializeNBT(nbt) }

    override fun withComposing(action: (GlyphComposing) -> Unit) {
        action.invoke(composing)
        nbt = composing.serializeNBT()
    }
}