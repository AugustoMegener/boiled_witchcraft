package kitowashere.boiled_witchcraft.common.data.glyph

import com.sun.jdi.InvalidTypeException
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import java.util.*
import kotlin.reflect.KProperty

abstract class GlyphData : INBTSerializable<CompoundTag> {

    inner class DataField<T>(var value: T) {

        operator fun getValue(glyphData: GlyphData, property: KProperty<*>) = value

        operator fun setValue(glyphData: GlyphData, property: KProperty<*>, t: T) { value = t
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
                else -> throw InvalidTypeException(
                    "${t!!::class.simpleName!!} isn't a valid DataField type. Please, read DataField documentation."
                )
            }
        }
    }

    var owner by DataField<UUID?>(null)

    private var nbt = CompoundTag()

    override fun serializeNBT(): CompoundTag = nbt
    override fun deserializeNBT(nbt: CompoundTag) { this.nbt = nbt }
}