package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.glyph.data.GlyphStack
import net.minecraft.core.component.DataComponentType
import net.neoforged.neoforge.registries.DeferredRegister

object DataComponentRegistry {
    val dataComponentRegistry: DeferredRegister.DataComponents = DeferredRegister.createDataComponents(ID)

    val glyphStackData = dataComponentRegistry.register("glyph") { ->
        DataComponentType.builder<GlyphStack>().persistent(GlyphStack.codec).build()
    }

}