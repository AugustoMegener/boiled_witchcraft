package kitowashere.boiled_witchcraft.common.data.glyph

import com.sun.jdi.InvalidTypeException
import kitowashere.boiled_witchcraft.common.data.glyph.editor.FieldEditor
import kitowashere.boiled_witchcraft.common.world.glyph.type.GlyphType
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.neoforged.neoforge.common.util.INBTSerializable
import java.util.*
import kotlin.reflect.KProperty

open class GlyphData : INBTSerializable<CompoundTag> {

    private var nbt: CompoundTag = CompoundTag()

    inner class DataField<T>(private val name: String,
                             private val init: T,
                             editor: ((DataField<T>, GlyphType?) -> FieldEditor<T>)? = null)
    {
        private var data: T = init
            set(value) {
                field = value

                when (value)  {
                    is Int                  -> nbt.putInt(name, value)
                    is Boolean              -> nbt.putBoolean(name, value)
                    is Byte                 -> nbt.putByte(name, value)
                    is ByteArray            -> nbt.putByteArray(name, value)
                    is Float                -> nbt.putFloat(name, value)
                    is IntArray             -> nbt.putIntArray(name, value)
                    is Long                 -> nbt.putLong(name, value)
                    is LongArray            -> nbt.putLongArray(name,value)
                    is Short                -> nbt.putShort(name, value)
                    is String               -> nbt.putString(name, value)
                    is UUID                 -> nbt.putUUID(name, value)
                    is Tag                  -> nbt.put(name, value)
                    is INBTSerializable<*>  -> nbt.put(name, value.serializeNBT())
                    else -> throw InvalidTypeException(
                    "${value!!::class.simpleName!!} isn't a valid DataField type. Please, read DataField documentation."
                    )
                }
            }

        init {
            editor?.let { e -> editorBuilders.add { e.invoke(this, it) } }


            @Suppress("UNCHECKED_CAST")
            data = when (init) {
                is Int                  -> nbt.getInt(name)         as T
                is Boolean              -> nbt.getBoolean(name)     as T
                is Byte                 -> nbt.getByte(name)        as T
                is ByteArray            -> nbt.getByteArray(name)   as T
                is Float                -> nbt.getFloat(name)       as T
                is IntArray             -> nbt.getIntArray(name)    as T
                is Long                 -> nbt.getLong(name)        as T
                is LongArray            -> nbt.getLongArray(name)   as T
                is Short                -> nbt.getShort(name)       as T
                is String               -> nbt.getString(name)      as T
                is UUID                 -> nbt.getUUID(name)        as T
                is Tag                  -> nbt.get(name)            as T
                is INBTSerializable<*> -> {
                    data = init
                    (data as INBTSerializable<CompoundTag>).deserializeNBT(nbt.getCompound(name))
                    data
                }
                else -> init
            }
        }


        operator fun getValue(glyphData: GlyphData, property: KProperty<*>): T =
            if (data == null) { setValue(glyphData, property, init); getValue(glyphData, property) } else data


        operator fun setValue(glyphData: GlyphData, property: KProperty<*>, t: T) { data = t }
    }

    private val editorBuilders = ArrayList<(GlyphType?) -> FieldEditor<*>>()

    fun getEditorBuilders() = editorBuilders.toTypedArray()

    var owner   by DataField("owner", UUID(0L, 0L))
    var size    by DataField("size", 1)


    override fun serializeNBT(): CompoundTag = nbt
    override fun deserializeNBT(nbt: CompoundTag) { this.nbt = nbt }
}

