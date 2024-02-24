package kitowashere.boiled_witchcraft.common.world.item

import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.fireGlyph
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class PencilItem(properties: Properties) : Item(properties) {

    override fun useOn(pContext: UseOnContext): InteractionResult {
        val level = pContext.level
        val pos = pContext.clickedPos

        val facing = pContext.clickedFace
        val glyphPos = pos.mutable().move(facing)

        if (!level.getBlockState(pos).isSolidRender(level, pos)) return InteractionResult.FAIL

        fireGlyph.get().putAsBlock(level, glyphPos, facing, 1, pContext.player)

        return InteractionResult.SUCCESS
    }
}