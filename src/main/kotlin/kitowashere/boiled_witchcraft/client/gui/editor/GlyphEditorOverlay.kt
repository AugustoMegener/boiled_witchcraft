package kitowashere.boiled_witchcraft.client.gui.editor

import io.kito.kore.client.gui.RegisterLayer
import io.kito.kore.client.gui.RegisterLayer.LayerRegisterMode
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.localPlayer
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.client.ClientData.isEditingGlyph
import kitowashere.boiled_witchcraft.client.gui.editor.option.EditorOptionWidget
import kitowashere.boiled_witchcraft.client.gui.editor.option.RegisterEditorOptionWidget.Companion.editorOptionWidgets
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.sprite
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
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

    private val xStart = 5
    private val yStart = 15

    @RegisterLayer("$ID:glyph_editor_overlay", LayerRegisterMode.ABOVE)
    fun GuiGraphics.render(delta: DeltaTracker) {
        if (!isEditingGlyph) return

        val editor = localPlayer!!.glyphEditor
        val stack = editor.stack

        var y = yStart
        var x = xStart

        val glyphName = translatable("glyph.${glyphRegistry.getKey(stack.glyph)!!.toLanguageKey()}")
            .let { when (editor.options.glyphToEditKind) {
                GlyphToEditKind.SOURCE -> it
                GlyphToEditKind.COMPOSITION -> literal("[").append(it).append("]")
            } }

        drawString(minecraftClient.font, glyphName, x, y, 0xffffff)

        y += minecraftClient.font.lineHeight * 2

        blit(x, y, 0, 32, 32, stack.sprite)

        y += 32

        var width = minecraftClient.font.width(glyphName)

        editor.options.all.map { it to (editorOptionWidgets[it.type] as EditorOptionWidget<GlyphData>) }
            .forEach{ (opt, wgt) -> opt as EditorOption<GlyphData, *>
                y += minecraftClient.font.lineHeight

                if (guiHeight() - y < wgt.getHeight(opt, stack.data)) {
                    x += width + 5
                    width = 0
                    y = yStart
                }

                wgt.render(opt, stack.data, opt == editor.options.selected, x, y, this, delta)

                width = max(width, wgt.getWidth(opt, stack.data))

                y += wgt.getHeight(opt, stack.data)
            }
    }
}