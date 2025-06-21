package kitowashere.boiled_witchcraft.client.gui.editor.option

import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics

@RegisterEditorOptionWidget("$ID:copy_option")
object CopyOptionWidget : EditorOptionWidget<GlyphData>() {
    override fun getWidgetWidth(option: EditorOption<GlyphData, *>, data: GlyphData) = 0

    override fun getWidgetHeight(option: EditorOption<GlyphData, *>, data: GlyphData) = 0

    override fun renderWidget(option: EditorOption<GlyphData, *>,
                              data: GlyphData,
                              isEditing: Boolean,
                              x: Int,
                              y: Int,
                              gui: GuiGraphics,
                              delta: DeltaTracker) {}
}