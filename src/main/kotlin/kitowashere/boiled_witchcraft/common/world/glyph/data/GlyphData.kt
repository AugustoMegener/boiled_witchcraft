package kitowashere.boiled_witchcraft.common.world.glyph.data

import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable

open class GlyphData(private var nbt: CompoundTag = CompoundTag()) : INBTSerializable<CompoundTag> {
    override fun serializeNBT() = nbt
    override fun deserializeNBT(newNbt: CompoundTag) { nbt = newNbt }
}


