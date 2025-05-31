package kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit

import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.Selector
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOptionType

abstract class EditorOptionKit<T : GlyphData> : Selector {

    private var options: List<EditorOption<*, *>> = listOf()

    override var index = 0

    override var range = 0..options.size

    @Suppress(UNCHECKED_CAST)
    var option: EditorOption<T, *> = run { setupOptions(); options[0] as EditorOption<T, *> }; private set

    @Suppress(UNCHECKED_CAST)
    override fun update() { option = options[index] as EditorOption<T, *> }

    abstract fun setupOptions()

    protected fun addOption(option: EditorOptionType<*>) { options += option.supplier() }
    protected fun addOptions(option: Collection<EditorOptionType<*>>) { options += option.map { it.supplier() } }
}