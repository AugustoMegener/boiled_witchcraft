package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.editorOptionTypeRegistryKey
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.*
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object EditorOptionTypes : SimpleRegister<EditorOptionType<*>>(ID, editorOptionTypeRegistryKey) {

    val   debugEndOption by "debug_end_option" { EditorOptionType(::DebugEndOption) }

    val   deleteOption by   "delete_option" { EditorOptionType(  ::DeleteOption) }
    val     sizeOption by     "size_option" { EditorOptionType(  ::SizeOption) }
    val   heightOption by   "height_option" { EditorOptionType(::HeightOption) }
}