package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphBlockEntity
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister


object BlockEntityRegistry {
    val blockEntityRegistry: DeferredRegister<BlockEntityType<*>> = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ID)

    val glyphBlockEntity: DeferredHolder<BlockEntityType<*>, BlockEntityType<GlyphBlockEntity>> =
        blockEntityRegistry.register("glyph_block") { -> BlockEntityType.Builder.of(
            { pos, state -> GlyphBlockEntity(pos, state) }
        ).build(null) }
}