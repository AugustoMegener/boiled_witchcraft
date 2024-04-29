package kitowashere.boiled_witchcraft.common.world.inventory.tooltip

import kitowashere.boiled_witchcraft.common.resource.canvas.GlyphCanvasManager
import kitowashere.boiled_witchcraft.common.resource.canvas.ItemGlyphCanvas
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.inventory.tooltip.TooltipComponent

data class GlyphStackTooltip(val canvas: ItemGlyphCanvas, val stack: GlyphStack) : TooltipComponent