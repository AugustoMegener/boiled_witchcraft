package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.google.common.collect.ImmutableMap
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.glyphFromID
import kitowashere.boiled_witchcraft.common.registry.GlyphRegistry.Util.id
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.placeholder
import kitowashere.boiled_witchcraft.common.world.glyph.NoneGlyph
import net.minecraft.nbt.CompoundTag
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException

class GlyphStack(glyph: Glyph = NoneGlyph, innerStack: GlyphStack? = null) : INBTSerializable<CompoundTag> {

    var glyph = glyph; private set
    var innerStack = innerStack; private set

    var data = glyph.newData(); private set

    private val glyphStacks = HashMap<Vector2i, GlyphStack>()
    val children: Map<Vector2i, GlyphStack> get() = ImmutableMap.copyOf(glyphStacks)

    val isEmpty get() = glyph == NoneGlyph
    val isHollow get() = glyph.isHollow(data)

    fun canSetInnerStack(stack: GlyphStack) =
        isHollow && !isEmpty && stack.data.size < data.size && stack.data.size % 2 == data.size % 2

    fun setInnerStack(stack: GlyphStack?) {
        if (stack == null) { innerStack = null }
        else if (!canSetInnerStack(stack)) throw ValueException("$stack can't be inner stack of $this")
        else innerStack = stack
    }

    fun canPut(pos: Vector2i, stack: GlyphStack) =
        !isEmpty && stack.data.size < data.size && !glyphStacks.containsKey(pos) && glyph.getSignal(data)[pos] ?: false

    fun put(pos: Vector2i, stack: GlyphStack) {
        if (!canPut(pos, stack)) throw ValueException("$stack can't be member of $this")
        glyphStacks[pos] = stack
    }

    override fun serializeNBT(): CompoundTag = if (isEmpty) CompoundTag() else CompoundTag().also {
        it.putString("glyph", glyph.id)
        it.put("data", data.serializeNBT())
        innerStack.takeIf { i -> i != null }?.also { i -> it.put("inner_stack", i.serializeNBT()) }

        it.put("stacks", CompoundTag().also { s ->
            glyphStacks.forEach { e -> s.put("${e.key.x}:${e.key.y}", e.value.serializeNBT()) }
        })
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        glyph = glyphFromID(nbt.getString("glyph")) ?: NoneGlyph
        data = glyph.newData().also { it.deserializeNBT(nbt.getCompound("data")) }

        setInnerStack(nbt.getCompound("inner_stack").takeIf { nbt.contains("inner_stack") }
                                                         ?.run { GlyphStack().also { it.deserializeNBT(this) } })
        glyphStacks.clear()
        nbt.getCompound("stacks").also { it.allKeys.forEach { key ->
            key.split(":").mapNotNull { p->p.toIntOrNull() }.takeIf { p->p.size >= 2 }?.also { p ->
                put(Vector2i(p[0], p[1]), GlyphStack().also { g -> g.deserializeNBT(it.getCompound(key)) })
            }
        } }
    }

    companion object {
        val empty = GlyphStack()
    }
}