package kitowashere.boiled_witchcraft.common.core.editor.glyph

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.client.core.glyph.Util.translatableName
import kitowashere.boiled_witchcraft.common.core.Selector
import kitowashere.boiled_witchcraft.common.core.editor.Editor
import kitowashere.boiled_witchcraft.common.util.KotlinUtil.Delegation
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphCategory.Companion.categories
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import kotlin.reflect.KMutableProperty
import net.minecraft.resources.ResourceLocation.parse as loc

class GlyphEditor(glyphDelegation: Delegation<GlyphStack>) : Editor(loc("$ID:glyph_editor")) {
    constructor(glyphProperty: KMutableProperty<GlyphStack>) : this(Delegation(glyphProperty))

    val categorySelect = CategorySelect()
    val    glyphSelect = GlyphSelect()
    val    fieldSelect = FieldSelect()
    var editSelect: DataField<*>
        get() = selectors[3] as DataField<*>
        set(value) { selectors[3] = value }

    var glyphStack by glyphDelegation

    override val selectors = arrayListOf(categorySelect, glyphSelect, fieldSelect, fieldSelect.value)

    override val valueComponent: MutableComponent = Component.empty()

    inner class CategorySelect : Selector<GlyphCategory>(loc("$ID:category_selector")) {
        override val maxIndex = categories.lastIndex
        override val valueComponent get() = value.name

        override fun valueFromIndex(i: Int) = categories[i]
        override fun selectPost(v: Int, value: GlyphCategory)
            { glyphSelect.wrappedIndex = 0 }
    }

    inner class GlyphSelect : Selector<Glyph>(loc("$ID:glyph_selector")) {
        override val maxIndex get() = categorySelect.value.content.lastIndex
        override val valueComponent get() = value.translatableName

        override fun valueFromIndex(i: Int) = categorySelect.value[i]
        override fun selectPost(v: Int, value: Glyph) {
            fieldSelect.wrappedIndex = 0
            glyphStack = value.newInstance()
        }
    }

    inner class FieldSelect : Selector<DataField<*>>(loc("$ID:field_selector")) {
        override val maxIndex get() = glyphStack.data.dataFields.lastIndex
        override val valueComponent = value.nameComponent

        override fun valueFromIndex(i: Int) = glyphStack.data.dataFields[i]
        override fun selectPost(v: Int, value: DataField<*>) { editSelect.value }
    }
}