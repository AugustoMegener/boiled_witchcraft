package kitowashere.boiled_witchcraft.common.world.glyph

import io.kito.kore.common.event.KSubscribe
import io.kito.kore.common.reflect.ObjectScanner
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.ResourceLocationExt.toLoc
import kitowashere.boiled_witchcraft.common.registry.Registries.editorOptionKitTypeRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.kit.RegisterGlyphEditorOptionKitEvent
import net.minecraft.resources.ResourceLocation
import net.neoforged.fml.ModContainer
import net.neoforged.neoforgespi.language.IModInfo
import kotlin.reflect.full.findAnnotation

@Scan
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class GlyphEditorKit(val id: String) {

    @Scan
    companion object {

        private val toRegister = arrayListOf<Pair<Glyph<*>, ResourceLocation>>()

        @ObjectScanner(Glyph::class)
        fun registerGlyphs(info: IModInfo, container: ModContainer, data: Glyph<*>) {
            toRegister += data to (data::class.findAnnotation<GlyphEditorKit>()?.id ?: return).toLoc()
        }

        @KSubscribe
        fun RegisterGlyphEditorOptionKitEvent.register() {
            toRegister.forEach { (g, l) -> editorOptionKitTypeRegistry[l]!! on { g } }
        }
    }
}
