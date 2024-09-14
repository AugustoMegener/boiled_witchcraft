package kitowashere.boiled_witchcraft.client.render.gui.overlay

import kitowashere.boiled_witchcraft.common.capabilities.Caps.Entity.entityGlyphEditor
import net.minecraft.client.DeltaTracker
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.LayeredDraw.Layer
import net.minecraft.network.chat.Component

object GlyphEditorOverlay : Layer {

    private val paddingX = 10
    private val paddingY = 10

    private val font          by lazy { Minecraft.getInstance().font }
    private val editorHandler by lazy { Minecraft.getInstance().player!!.getCapability(entityGlyphEditor)!! }
    private val renderers     by lazy {
        editorHandler.editor.stages.map { it to it.renderer?.invoke(editorHandler) }
    }


    override fun render(gui: GuiGraphics, delta: DeltaTracker) {
        var y = paddingY

        for ((stage, renderer) in renderers) {

            gui.drawString(font, Component.empty().append(stage.name).append(": ").append(stage.info), paddingX, y,
                           if (stage.isActual) 0xeba434 else 0xffffff)
            y += font.lineHeight + 2

            if (renderer != null && stage.isActual) {
                renderer.renderer(gui, delta, font, paddingX * 2, y)
                y += renderer.height(font) + 2
            }
        }
    }
}