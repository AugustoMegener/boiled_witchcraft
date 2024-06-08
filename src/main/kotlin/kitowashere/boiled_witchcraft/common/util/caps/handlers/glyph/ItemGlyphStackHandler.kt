package kitowashere.boiled_witchcraft.common.util.caps.handlers.glyph

import kitowashere.boiled_witchcraft.common.registry.DataComponentRegistry.glyphStackData
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.item.ItemStack

class ItemGlyphStackHandler(val itemStack: ItemStack) : GlyphStackHandler {

    override var stack: GlyphStack
        get() = itemStack.getOrDefault(glyphStackData, GlyphStack.empty)
        set(value) { itemStack.set(glyphStackData, value) }
}