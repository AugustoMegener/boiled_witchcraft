package kitowashere.boiled_witchcraft.common.world.inventory.tooltip

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.Item

data class GlyphStackTooltip(val item: Item, val stack: GlyphStack) : TooltipComponent {

}