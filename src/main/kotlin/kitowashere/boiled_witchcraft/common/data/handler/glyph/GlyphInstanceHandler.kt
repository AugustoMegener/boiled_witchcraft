package kitowashere.boiled_witchcraft.common.data.handler.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.fromID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.getID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable

interface GlyphInstanceHandler : INBTSerializable<CompoundTag> {
    var type: GlyphType<*>
    var data: GlyphData

    override fun deserializeNBT(nbt: CompoundTag) {
        type = fromID(nbt.getString("glyph")) ?: GlyphType.placeholder
        data = type.newData().also { if (nbt.contains("data")) it.deserializeNBT(nbt.getCompound("data")) }
    }

    override fun serializeNBT() = CompoundTag().also {
        it.putString("glyph", getID(type));
        it.put("data", data.serializeNBT())
    }
}