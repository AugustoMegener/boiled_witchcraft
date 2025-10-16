package kitowashere.boiled_witchcraft.common.world.glyph

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.kito.kore.util.UNCHECKED_CAST
import io.kito.kore.util.minecraft.jsonOps
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphCodec
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphStreamCodec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import org.joml.Vector2i
import java.util.*
import java.util.Optional.ofNullable
import kotlin.jvm.optionals.getOrNull

class GlyphStack(val data: GlyphData) : GlyphLike {

    constructor(glyph: Glyph<*>) : this(glyph.createData())

    override val glyph = data.type

    val isEmpty = glyph == EmptyGlyph

    override fun asStack() = this

    var inner: GlyphStack? = null; private set

    private var linked: HashMap<Vector2i, GlyphStack> = hashMapOf();

    val children get() = HashMap(linked)

    @Suppress(UNCHECKED_CAST)
    val isHollow get() = (glyph as Glyph<GlyphData>).isHollow(data)

    val size get() = data.size

    fun canInscribe(stack: GlyphStack) = isHollow && size > stack.size

    fun inscribe(stack: GlyphStack) {
        if (!canInscribe(stack)) throw IllegalStateException("Can't inscribe $stack on $this")
        inner = stack
    }

    @Suppress(UNCHECKED_CAST)
    fun isValidPos(pos: Vector2i) = (glyph as Glyph<GlyphData>).canLinkOn(pos, data)

    operator fun get(pos: Vector2i) = if (isValidPos(pos)) linked.computeIfAbsent(pos) { empty } else null

    operator fun set(pos: Vector2i, stack: GlyphStack) {
        if (!isValidPos(pos)) throw IllegalStateException("Can't link $stack on invalid $pos pos")
        linked[pos] = stack
    }

    fun copy() : GlyphStack =
        GlyphStack(dataCodec.decode(jsonOps, dataCodec.encodeStart(jsonOps, data).orThrow).orThrow.first).also {
            it.inner = inner?.copy()
            it.linked = hashMapOf(*linked.map { (k, i) -> k to i.copy() }.toTypedArray())
        }

    override fun toString() = "$glyph[$data] -> { inner=$inner; linked=$linked; }"

    companion object {

        val empty = GlyphStack(EmptyGlyph)

        val dataCodec: Codec<GlyphData> = glyphCodec().dispatch({ it: GlyphData -> it.type }) { it.dataCodec() }
        val dataStreamCodec = glyphStreamCodec().dispatch({ it: GlyphData -> it.type }) { it.dataStreamCodec() }

        val vector2iCodec: Codec<Vector2i> = RecordCodecBuilder.create { i ->
            i.group(
                Codec.INT.fieldOf("x").forGetter { it.x },
                Codec.INT.fieldOf("y").forGetter { it.y }
            ).apply(i) { x, y -> Vector2i(x, y) }
        }

        val vector2iStreamCodec = StreamCodec.composite(
            ByteBufCodecs.INT, { it.y },
            ByteBufCodecs.INT, { it.y }
        ) { x, y -> Vector2i(x, y) }


        val codec: Codec<GlyphStack> = Codec.recursive("glyph_stack") { codec ->
            RecordCodecBuilder.create { x ->
                x.group(
                    dataCodec.fieldOf("glyph").forGetter(GlyphStack::data),
                    Codec.optionalField("inner", codec, false).forGetter { ofNullable(it.inner) },
                    Codec.optionalField("linked", Codec.unboundedMap(vector2iCodec, codec), false)
                        .forGetter { if(it.linked.isEmpty()) Optional.empty() else Optional.of(it.linked) }
                ).apply(x) { d, i, l ->
                    GlyphStack(d).also { it.inner = i.getOrNull(); it.linked = HashMap(l.orElseGet { mapOf() }) }
                }
            }
        }

        val streamCodec: StreamCodec<RegistryFriendlyByteBuf, GlyphStack> = StreamCodec.recursive { sc ->
            StreamCodec.composite(
                dataStreamCodec, GlyphStack::data,
                ByteBufCodecs.optional(sc), { ofNullable(it.inner) },
                ByteBufCodecs.map(::LinkedHashMap, vector2iStreamCodec, sc), GlyphStack::linked
            ) { d, i, l -> GlyphStack(d).also { it.inner = i.getOrNull(); it.linked = HashMap(l) } }
        }
    }
}