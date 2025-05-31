package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.Registries.editorOptionTypeRegistryKey
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOptionType
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.HeightOption
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.SizeOption
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object EditorOptionTypes : SimpleRegister<EditorOptionType<*>>(ID, editorOptionTypeRegistryKey) {

    val   sizeOption by   "size_option" { EditorOptionType(  ::SizeOption) }
    val heightOption by "height_option" { EditorOptionType(::HeightOption) }
}