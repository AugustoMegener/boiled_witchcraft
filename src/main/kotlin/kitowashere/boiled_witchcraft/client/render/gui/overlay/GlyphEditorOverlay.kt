package kitowashere.boiled_witchcraft.client.render.gui.overlay

import kitowashere.boiled_witchcraft.client.core.glyph.EditorData
import kitowashere.boiled_witchcraft.client.util.ClientData.font
import kitowashere.boiled_witchcraft.client.util.ClientData.player
import kitowashere.boiled_witchcraft.common.capabilities.Caps.Entity.entityGlyphEditor
import kitowashere.boiled_witchcraft.common.core.editor.glyph.GlyphEditor
import kitowashere.boiled_witchcraft.common.util.GlyphUtil.canWriteGlyphOn
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.LayeredDraw.Layer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

typealias SelectorRendererBuilder = GlyphEditor.() -> EditorData.SectionRenderer

object GlyphEditorOverlay : Layer {

    val selectorRenderers = HashMap<ResourceLocation, SelectorRendererBuilder>()

    private val paddingX = 10
    private val paddingY = 10

    private val editorHandler by lazy { player.getCapability(entityGlyphEditor)!! }
    private val renderers     by lazy {
        editorHandler.map { it to selectorRenderers[it.location]?.invoke(editorHandler) }
    }

    override fun render(gui: GuiGraphics, delta: DeltaTracker) {
        if (player.canWriteGlyphOn == null) return

        var y = paddingY

        for ((stage, renderer) in renderers) {

            gui.drawString(font, Component.empty().append(stage.nameComponent).append(": ").append(stage.valueComponent), paddingX, y,
                           if (stage == editorHandler.value) 0xeba434 else 0xffffff)
            y += font.lineHeight + 2

            if (renderer != null && stage == editorHandler.value) {
                renderer.renderer(gui, delta, font, paddingX * 2, y)
                y += renderer.height(font) + 2
            }
        }
    }

    data class SelectorRenderer(val renderer: (gui: GuiGraphics, delta: DeltaTracker, font: Font, x: Int, y: Int) -> Unit,
                                val height: (Font) -> Int)
}