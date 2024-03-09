package kitowashere.boiled_witchcraft.common.world.glyph

import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.data.glyph.editor.FieldEditor
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.glyphTypeFromID
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.Util.glyphTypeID
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable

class Glyph(val type: GlyphType?) : INBTSerializable<CompoundTag> {

    var data = type?.newData() ?: GlyphData();  private set

    override fun serializeNBT() = CompoundTag().also {
        if (type != null) {
            it.putString("type", glyphTypeID(type))
            it.put("data", data.serializeNBT())
        }
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        data = type?.newData().also { it?.deserializeNBT(nbt.getCompound("data")) } ?: GlyphData()
    }

    fun getEditors(): Array<FieldEditor<*>> = data.getEditorBuilders().map { it.invoke(type) }.toTypedArray()

    companion object {
        val empty = Glyph(null)
        val placeholder; get() = GlyphType.placeholder.default()

        fun newFromNBT(nbt: CompoundTag) =
            Glyph(glyphTypeFromID(nbt.getString("type"))).also { it.deserializeNBT(nbt) }
    }
}