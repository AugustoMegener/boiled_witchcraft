package kitowashere.boiled_witchcraft.client.gui.editor.option

import io.kito.kore.util.minecraft.ResourceLocationExt.gui
import io.kito.kore.util.minecraft.ResourceLocationExt.png
import io.kito.kore.util.minecraft.ResourceLocationExt.texture
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component.literal

@RegisterEditorOptionWidget("$ID:size_option")
object SizeOptionWidget : EditorOptionWidget<GlyphData>() {

    val surfaceSptiteLocation = local("sprites/surface").gui.texture.png

    override fun getWidgetWidth (option: EditorOption<GlyphData, *>, data: GlyphData) =
        (2 * (data.size - 1) + 1) * 6

    override fun getWidgetHeight(option: EditorOption<GlyphData, *>, data: GlyphData) =
        (2 * (data.size - 1) + 1) * 3 + (minecraftClient.font.lineHeight * 2.5).toInt()

    override fun renderWidget(
        option: EditorOption<GlyphData, *>,
        data: GlyphData,
        isEditing: Boolean,
        x: Int,
        y: Int,
        gui: GuiGraphics,
        delta: DeltaTracker
    )
    {
        gui.drawString(
            minecraftClient.font,
            literal("${data.size}x${data.size}"),
            x, y, 0xd6d6d6
        )

        repeat(data.size) { xP ->
            repeat(data.size) { yP ->
                gui.blit(
                    surfaceSptiteLocation,
                    x + (xP - yP + (data.size - 1)) * 6,
                    y + (minecraftClient.font.lineHeight * 1.5).toInt() - 9 + (xP + yP) * 3,
                    0f, 0f, 16, 16, 16, 16
                )
            }
        }

    }
}