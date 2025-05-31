package kitowashere.boiled_witchcraft.common.world.glyph.editor.option

import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.editor.input.EditorInput
import kitowashere.boiled_witchcraft.common.world.glyph.editor.user.EditorUser
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

abstract class EditorOption<T: GlyphData, I: EditorInput>(private val clazz: KClass<I>,
                                                          val type: EditorOptionType<out EditorOption<T, I>>)
{
    @Suppress(UNCHECKED_CAST)
    fun use(editorUser: EditorUser, input: EditorInput, data: T){
        if (input::class.isSubclassOf(clazz)) onUsed(editorUser, input as I, data)
    }

    protected abstract fun onUsed(editorUser: EditorUser, input: I, data: T)
}