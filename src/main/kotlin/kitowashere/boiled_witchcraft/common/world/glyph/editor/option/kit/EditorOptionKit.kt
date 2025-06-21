package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.nbt.KNBTSerializable
import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.copyOption
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.deleteOption
import kitowashere.boiled_witchcraft.common.registry.EditorOptionTypes.placeGlyphOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.Selector
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOptionType

abstract class EditorOptionKit<T : GlyphData>(val glyphToEditKind: GlyphToEditKind) : Selector, KNBTSerializable {

    internal open val options = arrayListOf<EditorOption<*, *>>()

    val all: List<EditorOption<*, *>> get() = ArrayList(options)

    @Save
    override var index = 0

    override val range get() = all.indices
    
    @Suppress(UNCHECKED_CAST)
    var selected: EditorOption<T, *> = run { setupOptions(); all[0] as EditorOption<T, *> }; private set

    @Suppress(UNCHECKED_CAST)
    override fun update() { selected = all[index] as EditorOption<T, *> }

    abstract fun initOptions()

    protected fun addOption(option: EditorOptionType<*>) { options += option.supplier() }
    protected fun addOptions(option: Collection<EditorOptionType<*>>) { options += option.map { it.supplier() } }

    private fun setupOptions() {
        initOptions()
        addOption(copyOption)

        if (glyphToEditKind == GlyphToEditKind.COMPOSITION) {
            addOption(placeGlyphOption)
            addOption(deleteOption)
        }
    }
}