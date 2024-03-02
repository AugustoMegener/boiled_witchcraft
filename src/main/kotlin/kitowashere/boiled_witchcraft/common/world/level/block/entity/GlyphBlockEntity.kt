package kitowashere.boiled_witchcraft.common.world.level.block.entity

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.BlockEntityRegistry.glyphBlockEntity
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.fromID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.getID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class GlyphBlockEntity( pos: BlockPos, state: BlockState) : BlockEntity(glyphBlockEntity.get(), pos, state) {

    var glyphType: GlyphType<*> = GlyphType.placeholder
        set(value) { field = value; data = value.newData()}

    var data = glyphType.newData(); private set

    var size: Int = 0
        set(value) { field = glyphType.sizes.asSequence().filter { it >= value }.min() }


    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        glyphType = fromID(pTag.getString("glyph_type")) ?: GlyphType.placeholder
        data.deserializeNBT(pTag.getCompound("glyph_data"))
    }

    override fun saveAdditional(pTag: CompoundTag) {
        pTag.putString("glyph_type", getID(glyphType))
        pTag.put("glyph_data", data.serializeNBT())
        super.saveAdditional(pTag)
    }

    fun executes(executor: (GlyphType<*>, GlyphData) -> Unit) { executor.invoke(glyphType, data) }
}