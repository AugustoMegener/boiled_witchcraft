package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable

interface IGlyphHandler : INBTSerializable<CompoundTag> {
    var glyph: Glyph

    override fun deserializeNBT(nbt: CompoundTag) { glyph = Glyph.newFromNBT(nbt) }
    override fun serializeNBT() = glyph.serializeNBT()
}