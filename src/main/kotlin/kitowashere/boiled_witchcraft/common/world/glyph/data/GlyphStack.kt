package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.google.common.collect.ImmutableMap
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import kitowashere.boiled_witchcraft.common.world.glyph.NoneGlyph
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtOps
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.common.util.INBTSerializable
import org.joml.Vector2i
import org.openjdk.nashorn.internal.runtime.regexp.joni.exception.ValueException
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.optionals.getOrNull

class GlyphStack(glyph: Glyph = NoneGlyph, innerStack: GlyphStack? = null) : INBTSerializable<CompoundTag> {

    var glyph = glyph;           private set
    var data = glyph.newData();  private set

    var innerStack = innerStack; private set

    private var glyphStacks = HashMap<Vector2i, GlyphStack>()
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

    override fun serializeNBT(provider: HolderLookup.Provider) =
        codec.encode(this, provider.createSerializationContext(NbtOps.INSTANCE), CompoundTag()).result().get() as CompoundTag

    override fun deserializeNBT(provider: HolderLookup.Provider, nbt: CompoundTag) {
        (codec.parse(provider.createSerializationContext(NbtOps.INSTANCE), nbt).result().getOrNull() ?: return).also {
            glyph       = it.glyph
            data        = it.data
            innerStack  = it.innerStack
            glyphStacks = it.glyphStacks
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GlyphStack

        if (glyph != other.glyph) return false
        if (data != other.data) return false
        if (innerStack != other.innerStack) return false
        if (glyphStacks != other.glyphStacks) return false

        return true
    }

    override fun hashCode(): Int {
        var result = glyph.hashCode()
        result = 31 * result + data.hashCode()
        result = 31 * result + (innerStack?.hashCode() ?: 0)
        result = 31 * result + glyphStacks.hashCode()
        return result
    }


    companion object {
        val codec: Codec<GlyphStack> = Codec.recursive("glyph_stack") { c ->
            RecordCodecBuilder.create {
                it.group(
                    Glyph.codec.fieldOf("glyph").forGetter(GlyphStack::glyph),
                    GlyphData.codec.fieldOf("data").forGetter(GlyphStack::data),
                    c.optionalFieldOf("inner_stack").forGetter { g -> Optional.ofNullable(g.innerStack) },
                    inPosCodec.listOf().fieldOf("stacks").forGetter
                        { s -> s.glyphStacks.entries.map { e -> Pair(e.key, e.value) } }
                ).apply(it) { g, d, i, p ->

                    GlyphStack(g, i.getOrNull()).also { s ->
                        p.forEach { v -> if (s.canPut(v.first, v.second)) s.put(v.first, v.second) }
                    } .also { s -> s.data = d }
                }

            }
        }

        val inPosCodec: Codec<Pair<Vector2i, GlyphStack>> = RecordCodecBuilder.create {
            it.group(
                Codec.INT.listOf().fieldOf("pos").forGetter { s -> listOf(s.first.x, s.first.y) },
                codec.fieldOf("stack").forGetter(Pair<*, GlyphStack>::second),
            ).apply(it) { p, g -> Pair(Vector2i(p[0], p[1]), g)  }
        }

        val empty = GlyphStack()
    }
}