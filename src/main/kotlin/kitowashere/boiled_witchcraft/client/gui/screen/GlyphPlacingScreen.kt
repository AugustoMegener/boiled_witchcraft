package kitowashere.boiled_witchcraft.client.gui.screen

import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component.literal

object GlyphPlacingScreen : Screen(literal("")) {



    init { addRenderableWidget(GlyphPlacingListener) }
}