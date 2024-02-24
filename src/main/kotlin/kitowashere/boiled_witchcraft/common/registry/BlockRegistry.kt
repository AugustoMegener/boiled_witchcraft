package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft
import kitowashere.boiled_witchcraft.common.world.level.block.GlyphBlock
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.neoforged.neoforge.registries.DeferredRegister

object BlockRegistry {
    val blockRegistry: DeferredRegister.Blocks = DeferredRegister.createBlocks(BoiledWitchcraft.ID)

    val glyphBlock = blockRegistry.register("glyph_block"){ -> GlyphBlock(Properties.of())}
}