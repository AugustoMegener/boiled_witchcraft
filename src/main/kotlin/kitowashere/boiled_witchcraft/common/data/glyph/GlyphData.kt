package kitowashere.boiled_witchcraft.common.data.glyph

import com.sun.jdi.InvalidTypeException
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import java.util.*
import kotlin.reflect.KProperty

abstract class GlyphData(private var nbt: CompoundTag = CompoundTag()) : INBTSerializable<CompoundTag> {

    inner class DataField<T>(private val init: T?) {

        @Suppress("UNCHECKED_CAST")
        operator fun getValue(glyphData: GlyphData, property: KProperty<*>): T {
            val name = property.name
            return when (init) {
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
                is INBTSerializable<*>  -> nbt.get(name)            as T
                else -> {
                    setValue(glyphData, property, init)
                    return getValue(glyphData, property)
                }
            }
        }

        operator fun setValue(glyphData: GlyphData, property: KProperty<*>, t: T?) {

            val name = property.name
            when (t)  {
                is Int                  -> nbt.putInt(name, t)
                is Boolean              -> nbt.putBoolean(name, t)
                is Byte                 -> nbt.putByte(name, t)
                is ByteArray            -> nbt.putByteArray(name, t)
                is Float                -> nbt.putFloat(name, t)
                is IntArray             -> nbt.putIntArray(name, t)
                is Long                 -> nbt.putLong(name, t)
                is LongArray            -> nbt.putLongArray(name, t)
                is Short                -> nbt.putShort(name, t)
                is String               -> nbt.putString(name, t)
                is UUID                 -> nbt.putUUID(name, t)
                is INBTSerializable<*>  -> nbt.put(name, t.serializeNBT())
                null                    -> nbt.remove(name)
                else -> throw InvalidTypeException(
                    "${t!!::class.simpleName!!} isn't a valid DataField type. Please, read DataField documentation."
                )
            }
        }
    }

    var owner by DataField<UUID?>(null)

    override fun serializeNBT(): CompoundTag = nbt
    override fun deserializeNBT(nbt: CompoundTag) { this.nbt = nbt }

    @Suppress("UNCHECKED_CAST")
    fun <T: GlyphData>cast() = this as T
}