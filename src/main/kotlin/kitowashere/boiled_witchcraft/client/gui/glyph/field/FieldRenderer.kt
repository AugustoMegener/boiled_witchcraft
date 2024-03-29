package kitowashere.boiled_witchcraft.client.gui.glyph.field

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.client.gui.screens.Overlay
import net.minecraft.network.chat.MutableComponent

abstract class  FieldRenderer<T>(field: GlyphData.DataField<T>, protected val glyph: Glyph) : Overlay() {
    protected var field by field

    abstract val name: MutableComponent
    abstract val info: MutableComponent
}