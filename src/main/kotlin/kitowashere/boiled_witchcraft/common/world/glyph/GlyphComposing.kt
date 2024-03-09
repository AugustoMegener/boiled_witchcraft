package kitowashere.boiled_witchcraft.common.world.glyph

import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException

class GlyphComposing(coreGlyph: Glyph = Glyph.empty, coreOrbit: Glyph = Glyph.empty) : INBTSerializable<CompoundTag> {

    var coreGlyph = coreGlyph; private set(value) {
        if (value.isStructural) throw ValueException("Structural glyphs are not allowed here!")
        field = value
        if (coreOrbit.isEmpty && !value.isEmpty) size = value.data.size
    }
    var coreOrbit = coreOrbit; private set(value) {
        if (!value.isStructural) throw ValueException("Only structural glyphs allowed here!")
        field = value
        if (!coreOrbit.isEmpty) size = value.data.size
    }

    var size = coreGlyph.data.size; private set(value) { field = value; updateArea()           }

    private val glyphs = HashMap<Vector2i, Glyph>()
    private val structures = HashMap<Vector2i, Glyph>()

    private var area = Array(size) { Array(size) { true } }

    val glyphCount get() = glyphs.size

    fun getGlyphs() = HashMap(glyphs)

    private fun setArea(x: Int, y: Int, size: Int, value: Boolean) {
        for (xx in 0..size) for (yy in 0..size) area[x+xx][y+yy] = value
    }

    private fun updateArea() {
        area = Array(size) { Array(size) { true } }
        glyphs.forEach {
            val pos = it.key
            val glyphSize = it.value.data.size

            if (pos.x + glyphSize > size || pos.y + glyphSize > size) glyphs.remove(pos)
            else setArea(pos.x, pos.y, glyphSize, false)
        }
    }

    fun canPut(x: Int, y: Int, glyph: Glyph): Boolean {
        val glyphSize = glyph.data.size
        for (xx in 0..glyphSize) for (yy in 0..glyphSize) if (!area[x+xx][y+yy]) return false
        return true
    }

    fun put(x: Int, y: Int, glyph: Glyph): Boolean {
        if (!canPut(x, y, glyph)) return false

        glyphs[Vector2i(x, y)] = glyph

        val glyphSize = glyph.data.size
        setArea(x, y, glyphSize, false)
        return true
    }

    override fun serializeNBT(): CompoundTag = CompoundTag().also {
        it.put("core", coreGlyph.serializeNBT())
        it.put("components", CompoundTag().also { itt ->
            for (i in glyphs) itt.put("${i.key}:${i.key}", i.value.serializeNBT())
        })
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        coreGlyph = Glyph.newFromNBT(nbt.getCompound("core"))

        val glyphs = nbt.getCompound("components")
        glyphs.allKeys.forEach { key -> key.split(":").map { it.toInt() }.run {
            put(this[0], this[1], Glyph.newFromNBT(glyphs.getCompound(key)))
        }}
    }
}