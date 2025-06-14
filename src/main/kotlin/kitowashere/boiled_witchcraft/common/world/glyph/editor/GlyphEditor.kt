package kitowashere.boiled_witchcraft.common.world.glyph.editor

import io.kito.kore.common.data.Save
import io.kito.kore.common.data.nbt.KNBTSerializable
import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphLike
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.EditorInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.EditorOptionKit
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.EditorOptionKitType.Companion.newFrom
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.GlyphToEditKind
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.RegisterGlyphEditorOptionKitEvent.Companion.glyphEditorOptionKits
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class GlyphEditor(val user: GlyphAuthor) : Selector, KNBTSerializable {

    @Save
    override var index = 0

    private val allGlyphs: List<GlyphLike> get() = user.avaliableGlyphs + user.compositions

    override val range get() = 0..<(user.avaliableGlyphs.size+user.compositions.size)

    private val sourceRange get() = 0..<user.avaliableGlyphs.size

    var stack = user.avaliableGlyphs.getOrNull(index)?.asStack() ?: GlyphStack.empty
         private set(value) {
             field = value
             options = glyphEditorOptionKits[stack.glyph]!!.newFrom(kindOfIndex(index))
         }

    @Save
    var options = glyphEditorOptionKits[stack.glyph]!!.newFrom(kindOfIndex(index)); private set

    override fun update() {
        stack = allGlyphs[index].asStack()
    }

    @Suppress(UNCHECKED_CAST)
    fun useOption(input: EditorInput) {
        (options as EditorOptionKit<GlyphData>).selected.use(this, user, input, stack.data, stack)
    }

    fun acceptsInputOf(clazz: KClass<out EditorInput>) = clazz.isSubclassOf(options.selected.inputClazz)

    fun kindOfIndex(i: Int) =
        if (i in sourceRange) GlyphToEditKind.SOURCE else GlyphToEditKind.COMPOSITION

    fun compose() {
        if (!stack.glyph.isPrimary && !stack.glyph.isLinkable)
            throw IllegalStateException("Cant compose a non linkable or non primary glyph")

        user.addComposition(stack)
        index = range.max()
        update()
    }

    fun removeComposition() {
        if (kindOfIndex(index) == GlyphToEditKind.SOURCE)
            throw IllegalStateException("not a composition on index $index")

        user.removeComposition(index - user.avaliableGlyphs.size)
        index -= 1
        update()
    }
}