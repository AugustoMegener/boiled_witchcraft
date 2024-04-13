package kitowashere.boiled_witchcraft.common.caps.handlers.glyph

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStackAttach
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.item.ItemStack

class ItemGlyphStackHandler(val itemStack: ItemStack) : GlyphStackHandler {
    override var stack: GlyphStack
        get() = itemStack.getData(glyphStackAttach)
        set(value) { itemStack.setData(glyphStackAttach, value) }
}