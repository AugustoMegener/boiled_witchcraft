package kitowashere.boiled_witchcraft.client.gui.editor.option

import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import net.minecraft.ChatFormatting
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics
import kotlin.math.max

abstract class EditorOptionWidget<T : GlyphData>() {

    protected abstract fun getWidgetWidth(option: EditorOption<T, *>, data: T): Int
    protected abstract fun getWidgetHeight(option: EditorOption<T, *>, data: T): Int

    fun getWidth(option: EditorOption<T, *>, data: T) =
        max(minecraftClient.font.width(option.title), getWidgetWidth(option, data))

    fun getHeight(option: EditorOption<T, *>, data: T) =
        minecraftClient.font.lineHeight * 2 + getWidgetHeight(option, data)

    fun render(option: EditorOption<T, *>, data: T, isEditing: Boolean, x: Int, y: Int, gui: GuiGraphics, delta: DeltaTracker) {

        gui.drawString(minecraftClient.font, option.title.copy().let { if (isEditing) it.withStyle(ChatFormatting.UNDERLINE) else it }, x, y, 0xffffff)

        renderWidget(option, data, isEditing, x, y + minecraftClient.font.lineHeight * 2, gui, delta)
    }

    abstract fun renderWidget(option: EditorOption<T, *>, data: T, isEditing: Boolean, x: Int, y: Int, gui: GuiGraphics, delta: DeltaTracker)
}