package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.glyphTypeFromID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.glyphTypeID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType.GlyphKind
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable

class Glyph(type: GlyphType? = null) : INBTSerializable<CompoundTag> {

    var type = type; private set

    val isEmpty      = this == empty
    val isStructural = type?.kind == GlyphKind.STRUCTURAL

    var data = type?.newData() ?: GlyphData();  private set
    var editors = data.getEditors(this); private set
    var renderers = data.getRenderers(this); private set


    override fun serializeNBT() = CompoundTag().also {
        if (type != null) {
            it.putString("type", glyphTypeID(type!!))
            it.put("data", data.serializeNBT())
        }
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        type = glyphTypeFromID(nbt.getString("type"))
        data = type?.newData().also { it?.deserializeNBT(nbt.getCompound("data")) } ?: GlyphData()
        editors = data.getEditors(this)
    }

    companion object {
        val empty = Glyph(null)
        val placeholder; get() = GlyphType.placeholder.default()

        fun newFromNBT(nbt: CompoundTag) =
            Glyph(glyphTypeFromID(nbt.getString("type"))).also { it.deserializeNBT(nbt) }
    }
}