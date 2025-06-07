package kitowashere.boiled_witchcraft.client.gui.editor.option

import io.kito.kore.util.minecraft.ResourceLocationExt.gui
import io.kito.kore.util.minecraft.ResourceLocationExt.png
import io.kito.kore.util.minecraft.ResourceLocationExt.texture
import io.kito.kore.util.minecraft.minecraftClient
import kitowashere.boiled_witchcraft.BoiledWitchcraft.local
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.PillarGlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import net.minecraft.client.DeltaTracker
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component.literal
import net.minecraft.network.chat.Component.translatable

@RegisterEditorOptionWidget("$ID:height_option")
object HeightOptionWidget : EditorOptionWidget<PillarGlyphData>() {

    val cubeSptiteLocation = local("sprites/cube").gui.texture.png

    override fun getWidgetWidth(option: EditorOption<PillarGlyphData, *>, data: PillarGlyphData) =
        (2 * (data.size - 1) + 1) * 6

    override fun getWidgetHeight(option: EditorOption<PillarGlyphData, *>, data: PillarGlyphData) =
        ((2 * (data.size - 1)) * 3 + 16 + (data.height - 1) * 9) + (minecraftClient.font.lineHeight * 2.5).toInt()

    override fun renderWidget(
        option: EditorOption<PillarGlyphData, *>,
        data: PillarGlyphData,
        isEditing: Boolean,
        x: Int,
        y: Int,
        gui: GuiGraphics,
        delta: DeltaTracker
    )
    {
        gui.drawString(
            minecraftClient.font,
            literal("${data.height} ").append(
                translatable("editor.$ID.block".let { if (data.height > 1) it + "s" else it})
            ),
            x, y, 0xd6d6d6
        )

        repeat(data.size) { xP ->
            repeat(data.size) { yP ->
                for (zP in data.height - 1 downTo 0) {
                    gui.blit(
                        cubeSptiteLocation,
                        x + (xP - yP + (data.size - 1)) * 6,
                        y + (minecraftClient.font.lineHeight * 1.5 ).toInt() + (xP + yP) * 3 + zP * 9,
                        0f, 0f, 16, 16, 16, 16
                    )
                }
            }
        }
    }
}