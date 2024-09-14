package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries.DATA_COMPONENT_TYPE

object DataComponentRegistry : Register<DataComponentType<*>>(DATA_COMPONENT_TYPE) {

    val glyphStackData = "glyph" by { DataComponentType.builder<GlyphStack>().persistent(GlyphStack.codec).build() }
}