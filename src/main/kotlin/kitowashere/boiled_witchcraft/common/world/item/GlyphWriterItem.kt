package kitowashere.boiled_witchcraft.common.world.item

import kitowashere.boiled_witchcraft.common.registry.AttachRegistry.glyphStack
import kitowashere.boiled_witchcraft.common.resource.GlyphStackCanvasManager.glyphCanvas
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionHand.MAIN_HAND
import net.minecraft.world.InteractionHand.OFF_HAND
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

open class GlyphWriterItem(durability: Int, properties: Properties) : Item(properties.durability(durability)) {

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val writerItem = pPlayer.getItemInHand(pUsedHand)
        val canvasItem = pPlayer.getItemInHand(when (pUsedHand) { MAIN_HAND -> OFF_HAND; OFF_HAND -> MAIN_HAND })

        return canvasItem.glyphCanvas?.run {
            if (this.glyphStack.isEmpty) {
                writerItem.hurtAndBreak(1, pPlayer) {}
                canvasItem.shrink(0)
                pPlayer.addItem(ItemStack(canvasItem.item).also { it.glyphCanvas!!.glyphStack = pPlayer.glyphStack })

                InteractionResultHolder.success(writerItem)
            } else InteractionResultHolder.fail(writerItem)
        } ?:       InteractionResultHolder.pass(writerItem)
    }
}