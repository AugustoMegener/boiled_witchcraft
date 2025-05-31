package kitowashere.boiled_witchcraft.common.world.glyph

import io.kito.kore.common.reflect.ObjectScanner
import io.kito.kore.common.reflect.Scan
import kitowashere.boiled_witchcraft.common.registry.Registries.glyphRegistryKey
import net.neoforged.fml.ModContainer
import net.neoforged.neoforge.registries.DeferredRegister
import net.neoforged.neoforgespi.language.IModInfo
import kotlin.reflect.full.findAnnotation

@Scan
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterGlyph(val id: String) {

    @Scan
    companion object {

        @ObjectScanner(Glyph::class)
        fun registerGlyphs(info: IModInfo, container: ModContainer, data: Glyph<*>) {
            val id = data::class.findAnnotation<RegisterGlyph>()?.id ?: return

            DeferredRegister.create(glyphRegistryKey, info.modId).run {
                register(id) { -> data }
                register(container.eventBus!!)
            }
        }
    }
}
