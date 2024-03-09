package kitowashere.boiled_witchcraft.common.world.item

import kitowashere.boiled_witchcraft.common.data.Caps
import kitowashere.boiled_witchcraft.common.world.inventory.tooltip.GlyphComposingTooltip
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import java.util.*

class GlyphOnAPaperItem(properties: Properties) : Item(properties) {

    override fun getTooltipImage(pStack: ItemStack): Optional<TooltipComponent>  {
        var result = Optional.empty<TooltipComponent>()

        pStack.getCapability(Caps.Glyph.Composing.item)
            ?.withComposing { result = Optional.of(GlyphComposingTooltip(it)) }

        return result
    }

}