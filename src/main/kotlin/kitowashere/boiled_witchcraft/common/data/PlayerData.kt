package kitowashere.boiled_witchcraft.common.data

import net.minecraft.world.entity.player.Player

object PlayerData {
    private val isWiringMap = HashMap<Player, Boolean>()

    fun Player.consumeWiringAction() =
        isWiringMap.computeIfAbsent(this) { false } .also { isWiringMap[this] = !isWiringMap[this]!! }
}