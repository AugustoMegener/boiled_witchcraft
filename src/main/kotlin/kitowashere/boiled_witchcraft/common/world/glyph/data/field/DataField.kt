package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph.GlyphEditorHandler
import kitowashere.boiled_witchcraft.common.util.Wrapable
import net.minecraft.network.chat.MutableComponent
import kotlin.reflect.KProperty

abstract class DataField<T>(val name: String, initIndex: Int) : Wrapable {

    final override var wrappedIndex = initIndex
        set(value) {
            field = value
            this.value = valueFromIndex
        }

    abstract val valueFromIndex: T
    abstract var value: T

    open val renderer: (GlyphEditorHandler.() -> SectionRenderer)? = null

    abstract val nameComponent : MutableComponent
    abstract val valueComponent: MutableComponent

    operator fun getValue(cls: Any, property: KProperty<*>) = value
    operator fun setValue(cls: Any, property: KProperty<*>, v: T) { value = v}
}