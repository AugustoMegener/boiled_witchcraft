package kitowashere.boiled_witchcraft.client.gui.editor.option

import io.kito.kore.common.reflect.ObjectScanner
import io.kito.kore.common.reflect.Scan
import io.kito.kore.util.minecraft.ResourceLocationExt.toLoc
import kitowashere.boiled_witchcraft.common.registry.Registries.editorOptionTypeRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.editor.option.EditorOptionType
import net.neoforged.fml.ModContainer
import net.neoforged.neoforgespi.language.IModInfo
import kotlin.reflect.full.findAnnotation

@Scan
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterEditorOptionWidget(val editorOptionId: String) {

    @Scan
    companion object {

        private val entries = arrayListOf<Pair<() -> EditorOptionType<*>, EditorOptionWidget<*>>>()

        val editorOptionWidgets by lazy { entries.associate { it.first() to it.second } }

        @ObjectScanner(EditorOptionWidget::class)
        fun registerWidget(info: IModInfo, container: ModContainer, data: EditorOptionWidget<*>) {
            val id = data::class.findAnnotation<RegisterEditorOptionWidget>()?.editorOptionId?.toLoc() ?: return

            entries += { editorOptionTypeRegistry[id]!! } to data
        }
    }
}
