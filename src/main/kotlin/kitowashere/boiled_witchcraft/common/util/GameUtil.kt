package kitowashere.boiled_witchcraft.common.util

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionHand.MAIN_HAND
import net.minecraft.world.InteractionHand.OFF_HAND

object GameUtil {

    val InteractionHand.opposite get() = if (this == MAIN_HAND) OFF_HAND else MAIN_HAND
}