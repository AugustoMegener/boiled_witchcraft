package kitowashere.boiled_witchcraft.common.world.glyph.type

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceKey.createRegistryKey
import net.minecraft.resources.ResourceLocation


abstract class GlyphType(val type: GlyphKind, val sizes: List<Int> = listOf(1, 2, 3), private val data: () -> GlyphData)
{
    fun newData() = data.invoke()



    companion object {
        val registryKey: ResourceKey<Registry<GlyphType>> = createRegistryKey(ResourceLocation("glyph_types"))
    }

    enum class GlyphKind { PRIMARY, COMPOUND }
}