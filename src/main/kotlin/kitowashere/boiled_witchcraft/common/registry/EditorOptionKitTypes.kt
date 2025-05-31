package kitowashere.boiled_witchcraft.common.registry

import io.kito.kore.common.reflect.Scan
import io.kito.kore.common.registry.SimpleRegister
import kitowashere.boiled_witchcraft.ID
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.Registries.editorOptionKitTypeRegistryKey
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.EditorOptionKitType
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.PillarOptionKit
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.SimpleOptionKit
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Scan
object EditorOptionKitTypes : SimpleRegister<EditorOptionKitType<*>>(ID, editorOptionKitTypeRegistryKey) {

    val simpleOpitonKit by "simple_option_kit" {
        EditorOptionKitType<SimpleOptionKit<*>> { SimpleOptionKit<GlyphData>() }
    }

    val pillarOpitonKit by "pillar_option_kit" {
        EditorOptionKitType<PillarOptionKit<*>> { PillarOptionKit<GlyphData>() }
    }
}