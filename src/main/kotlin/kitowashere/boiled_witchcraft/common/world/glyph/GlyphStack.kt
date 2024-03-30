package kitowashere.boiled_witchcraft.common.world.glyph

import com.google.common.collect.ImmutableMap
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.glyphFromID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.placeholder
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i

class GlyphStack(glyph: Glyph = placeholder) : INBTSerializable<CompoundTag> {
    var glyph = glyph; private set
    var data = glyph.newData(); private set

    private val glyphStacks = HashMap<Vector2i, GlyphStack>()

    val children get() = ImmutableMap.copyOf(glyphStacks)

    fun canPut(pos: Vector2i, stack: GlyphStack) = !glyphStacks.containsKey(pos) && glyph.signal[pos] ?: false

    fun put(pos: Vector2i, stack: GlyphStack): Boolean {
        if (!canPut(pos, stack))  return false
        glyphStacks[pos] = stack; return true
    }

    override fun serializeNBT(): CompoundTag = CompoundTag().also {
        it.putString("glyph", glyph.id)
        it.put("data", data.serializeNBT())

        it.put("stacks", CompoundTag().also { s ->
            glyphStacks.forEach { e ->
                s.put("${e.key.x}:${e.key.y}", e.value.serializeNBT())
            }
        })
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        glyph = glyphFromID(nbt.getString("glyph")) ?: placeholder
        data = glyph.newData().also { it.deserializeNBT(nbt.getCompound("data")) }

        glyphStacks.clear()

        nbt.getCompound("stacks").also { it.allKeys.forEach { key ->
            key.split(":").mapNotNull { p->p.toIntOrNull() }.takeIf { p->p.size >= 2 }?.also { p ->
                put(Vector2i(p[0], p[1]), GlyphStack().also { g -> g.deserializeNBT(it.getCompound(key)) })
            }
        } }
    }
}