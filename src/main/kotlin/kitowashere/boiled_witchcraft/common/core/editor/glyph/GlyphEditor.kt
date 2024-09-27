package kitowashere.boiled_witchcraft.common.core.editor.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.Util.translatableName
import kitowashere.boiled_witchcraft.common.core.editor.Editor
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation.parse as loc

open class GlyphEditor(val categories: List<GlyphCategory>) : Editor<GlyphStack>() {
    override var result = Glyph.placeholder.newStack()

    val categorySelect = object : EditorSelect<GlyphCategory>() {
        override val options = categories

        override val location = loc("$ID:category_select")
        override val nameComponent = Component.translatable("editor.stages.$ID.category")
        override val valueComponent get() = value.name

        override fun newResult(old: GlyphStack) = value[0].newStack()
    }

    val glyphSelect = object : EditorSelect<Glyph>() {
        override val options get() = categorySelect.value.content

        override val location = loc("$ID:glyph_select")
        override val nameComponent = Component.translatable("editor.stages.$ID.glyph")
        override val valueComponent get() = value.translatableName


        override fun newResult(old: GlyphStack) = value.newStack()
    }

    val fieldSelect = object : EditorSelect<DataField<*>>() {
        override val location = loc("$ID:field_select")
        override val nameComponent = Component.translatable("editor.stages.$ID.field")
        override val valueComponent get() = value.nameComponent

        override val options get() = result.data.dataFields
        override fun newResult(old: GlyphStack) = old
    }

    val editSelect = object : EditorSelect<Any?>() {
        override val location = loc("$ID:edit_select")
        override val nameComponent  get() = fieldSelect.value.nameComponent
        override val valueComponent get() = fieldSelect.value.valueComponent

        override val options get() = fieldSelect.value.options
        override fun newResult(old: GlyphStack) = old.also { fieldSelect.value.wrappedIndex = wrappedIndex }
    }

    override val options = listOf(categorySelect, glyphSelect, fieldSelect, editSelect)
}