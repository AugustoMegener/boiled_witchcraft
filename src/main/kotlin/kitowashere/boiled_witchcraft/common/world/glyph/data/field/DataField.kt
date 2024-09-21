package kitowashere.boiled_witchcraft.common.world.glyph.data.field

import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.common.capabilities.handlers.glyph.GlyphEditorHandler
import kitowashere.boiled_witchcraft.common.core.Selector
import net.minecraft.resources.ResourceLocation
import kotlin.reflect.KProperty

abstract class DataField<T : Any>(location: ResourceLocation, val name: String, initIndex: Int = 0, )
    : Selector<T>(location)
{
    init { wrappedIndex = initIndex }

    open val renderer: (GlyphEditorHandler.() -> SectionRenderer)? = null

    abstract fun T.asIndex() : Int

    operator fun getValue(cls: Any, property: KProperty<*>) = value
    operator fun setValue(cls: Any, property: KProperty<*>, v: T) { wrappedIndex = v.asIndex() }
}