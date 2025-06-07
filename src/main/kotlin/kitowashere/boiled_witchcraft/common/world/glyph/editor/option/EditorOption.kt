package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.GlyphStack
import kitowashere.boiled_witchcraft.common.world.glyph.author.GlyphAuthor
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.EditorInput
import net.minecraft.network.chat.Component
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

abstract class EditorOption<T: GlyphData, I: EditorInput>(val inputClazz: KClass<I>,
                                                          val type: EditorOptionType<out EditorOption<T, I>>)
{
    abstract val title: Component

    @Suppress(UNCHECKED_CAST)
    fun use(glyphAuthor: GlyphAuthor, input: EditorInput, data: T, stack: GlyphStack){
        if (input::class.isSubclassOf(inputClazz)) onUsed(glyphAuthor, input as I, data, stack)
    }

    protected abstract fun onUsed(glyphAuthor: GlyphAuthor, input: I, data: T, stack: GlyphStack)
}