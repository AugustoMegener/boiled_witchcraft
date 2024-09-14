package kitowashere.boiled_witchcraft.client.util

import net.minecraft.client.Minecraft

object ClientData {
    val minecraft by lazy { Minecraft.getInstance() }

    val player    by lazy { minecraft.player!! }
    val font      by lazy { minecraft.font }
}