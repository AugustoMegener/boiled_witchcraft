package kitowashere.boiled_witchcraft.client.gui.editor

import io.kito.kore.client.gui.RegisterLayer
import io.kito.kore.client.gui.RegisterLayer.LayerRegisterMode
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.UNCHECKED_CAST
import io.kito.kore.util.minecraft.localPlayer
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.client.ClientData.isEditingGlyph
import kitowashere.boiled_witchcraft.client.gui.editor.option.EditorOptionWidget
import kitowashere.boiled_witchcraft.client.gui.editor.option.RegisterEditorOptionWidget.Companion.editorOptionWidgets
import kitowashere.boiled_witchcraft.client.render.glyph.GuiGlyphRender
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphClipboard
import kitowashere.boiled_witchcraft.common.registry.DataAttachTypes.glyphEditor
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.GlyphToEditKind
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component.literal
import net.minecraft.network.chat.Component.translatable
import kotlin.math.max

@Scan
object GlyphEditorOverlay {

    private const val X_START = 5
    private const val Y_START = 15

    @RegisterLayer("$ID:glyph_editor_overlay", LayerRegisterMode.ABOVE)
    fun GuiGraphics.render(delta: DeltaTracker) {
        if (!isEditingGlyph) return

        val editor = localPlayer!!.glyphEditor
        val stack = editor.stack

        var y = Y_START
        var x = X_START

        val glyphName = translatable("glyph.${glyphRegistry.getKey(stack.glyph)!!.toLanguageKey()}")
            .let { when (editor.options.glyphToEditKind) {
                GlyphToEditKind.SOURCE -> it
                GlyphToEditKind.COMPOSITION -> literal("[").append(it).append("]")
            } }

        drawString(minecraftClient.font, glyphName, x, y, 0xffffff)

        val glyphRender = GuiGlyphRender(stack, 16)

        y += minecraftClient.font.lineHeight * 2
        glyphRender.render(this, x, y)

        y += glyphRender.heightInPixels
        var width = max(minecraftClient.font.width(glyphName), glyphRender.widthInPixels)

        @Suppress(UNCHECKED_CAST)
        editor.options.all.map { it to (editorOptionWidgets[it.type] as EditorOptionWidget<GlyphData>) }
            .forEach{ (opt, wgt) -> opt as EditorOption<GlyphData, *>
                y += minecraftClient.font.lineHeight

                if (guiHeight() - y < wgt.getHeight(opt, stack.data)) {
                    x += width + 5
                    width = 0
                    y = Y_START
                }

                wgt.render(opt, stack.data, opt == editor.options.selected, x, y, this, delta)

                width = max(width, wgt.getWidth(opt, stack.data))

                y += wgt.getHeight(opt, stack.data)
            }

        val clipboardGlyphRender = GuiGlyphRender(editor.user.clipBoard, 16)

        if (guiHeight() - y < clipboardGlyphRender.heightInPixels + minecraftClient.font.lineHeight * 3.5) {
            x += width + 5
            y = Y_START
        } else y += minecraftClient.font.lineHeight * 2


        if (!localPlayer!!.glyphClipboard.isEmpty) {
            drawString(minecraftClient.font, literal("Clipboard"), x, y, 0xffffff)

            y += (minecraftClient.font.lineHeight * 1.5).toInt()
            clipboardGlyphRender.render(this, x, y)
        }
    }
}