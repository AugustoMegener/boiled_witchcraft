package kitowashere.boiled_witchcraft.common.world.inventory.tooltip

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.world.inventory.tooltip.TooltipComponent

data class GlyphTooltip(val glyph: GlyphType<*>, val glyphData: GlyphData) : TooltipComponent