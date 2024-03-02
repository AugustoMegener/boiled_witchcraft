package kitowashere.boiled_witchcraft.common.world.item

import kitowashere.boiled_witchcraft.common.registry.ItemRegistry.glyphOnAPaper
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionHand.MAIN_HAND
import net.minecraft.world.InteractionHand.OFF_HAND
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items.PAPER
import net.minecraft.world.level.Level

class PencilItem(properties: Properties) : Item(properties) {

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val self = pPlayer.getItemInHand(pUsedHand)
        val other = pPlayer.getItemInHand(when (pUsedHand) { MAIN_HAND -> OFF_HAND; OFF_HAND -> MAIN_HAND })

        if (other.`is`(PAPER)) {
            other.shrink(1)
            pPlayer.addItem(glyphOnAPaper.get().defaultInstance)
            used(self, pPlayer)

                return InteractionResultHolder.success(self)
        } else  return InteractionResultHolder.fail(   self)
    }

    private fun used(stack: ItemStack, player: Player) {
        stack.hurtAndBreak(1, player) {}
        player.cooldowns.addCooldown(stack.item, 40)
    }
}