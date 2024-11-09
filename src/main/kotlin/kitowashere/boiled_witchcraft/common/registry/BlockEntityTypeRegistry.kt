package kitowashere.boiled_witchcraft.common.registry

import kitowashere.boiled_witchcraft.common.registry.BlockRegistry.glyphDeskBlock
import kitowashere.boiled_witchcraft.common.world.level.block.entity.GlyphDeskBlockEntity
import net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE
import net.minecraft.world.level.block.entity.BlockEntityType
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object BlockEntityTypeRegistry : Register<BlockEntityType<*>>(BLOCK_ENTITY_TYPE) {

    val glyphDeskBlockEntityType: BlockEntityType<GlyphDeskBlockEntity> by
        "glyph_desk" by { BlockEntityType.Builder.of(::GlyphDeskBlockEntity, glyphDeskBlock).build(null) }
}