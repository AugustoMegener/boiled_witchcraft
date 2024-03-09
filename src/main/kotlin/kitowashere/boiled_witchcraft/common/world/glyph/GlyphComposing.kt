package kitowashere.boiled_witchcraft.common.world.glyph

import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i

class GlyphComposing(coreGlyph: Glyph = Glyph.empty) : INBTSerializable<CompoundTag> {

    var coreGlyph = coreGlyph; private set(value) { field = value; size = value.data.size }
    var size = coreGlyph.data.size; private set

    private val glyphs = HashMap<Vector2i, Glyph>()
    private val area = Array(size) { Array(size) { true } }

    val glyphCount get() = glyphs.size

    fun getGlyphs() = HashMap(glyphs)

    fun canPut(x: Int, y: Int, glyph: Glyph): Boolean {
        val glyphSize = glyph.data.size
        for (xx in 0..glyphSize) for (yy in 0..glyphSize) if (!area[x+xx][y+yy]) return false
        return true
    }

    fun put(x: Int, y: Int, glyph: Glyph): Boolean {
        if (canPut(x, y, glyph)) return false

        glyphs[Vector2i(x, y)] = glyph

        val glyphSize = glyph.data.size
        for (xx in 0..glyphSize) for (yy in 0..glyphSize) area[x+xx][y+yy] = false
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