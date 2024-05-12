package kitowashere.boiled_witchcraft.common.world.inventory.tooltip

import kitowashere.boiled_witchcraft.common.resource.CanvasRegistry.ItemCanvas.ItemCanvasData
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.world.inventory.tooltip.TooltipComponent

data class GlyphStackTooltip(val canvas: ItemCanvasData, val stack: GlyphStack) : TooltipComponent