package kitowashere.boiled_witchcraft.client.gui.overlay

import kitowashere.boiled_witchcraft.common.data.glyph.editor.PlayerGlyphEditor.Companion.glyphEditor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Overlay
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
object GlyphConfigOverlay : Overlay() {
    val editor = Minecraft.getInstance().player!!.glyphEditor

    override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
        TODO("Not yet implemented")
    }

}