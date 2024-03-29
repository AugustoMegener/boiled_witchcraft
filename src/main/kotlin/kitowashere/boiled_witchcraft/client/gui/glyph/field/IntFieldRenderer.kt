package kitowashere.boiled_witchcraft.client.gui.glyph.field

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

open class IntFieldRenderer(override val name: MutableComponent, field: GlyphData.DataField<Int>, glyph: Glyph)
    : FieldRenderer<Int>(field, glyph)
{
    override val info; get() = Component.translatable("field.info.$ID.int").append(": $field")

    override fun render(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {}
}