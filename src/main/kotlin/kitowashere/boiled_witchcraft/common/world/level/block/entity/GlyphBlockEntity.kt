package kitowashere.boiled_witchcraft.common.world.level.block.entity

import kitowashere.boiled_witchcraft.common.registry.BlockEntityRegistry.glyphBlockEntity
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class GlyphBlockEntity(
    pos: BlockPos,
    state: BlockState,
    glyphType: GlyphType
) : BlockEntity(glyphBlockEntity.get(), pos, state)
{
    val data = glyphType.newData()

    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        data.deserializeNBT(pTag.getCompound("glyph_data"))
    }

    override fun saveAdditional(pTag: CompoundTag) {
        pTag.put("glyph_data", data.serializeNBT())
        super.saveAdditional(pTag)
    }
}