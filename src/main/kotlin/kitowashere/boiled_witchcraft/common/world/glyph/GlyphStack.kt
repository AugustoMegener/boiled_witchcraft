package kitowashere.boiled_witchcraft.common.world.glyph

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.kito.kore.util.UNCHECKED_CAST
import kitowashere.boiled_witchcraft.common.data.glyph.GlyphData
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph.Companion.glyphCodec
import org.joml.Vector2i
import java.util.*
import kotlin.jvm.optionals.getOrNull

class GlyphStack(val data: GlyphData) : GlyphLike {

    constructor(glyph: Glyph<*>) : this(glyph.createData())

    override val glyph = data.type

    override fun asStack() = this

    var inner: GlyphStack? = null; private set

    private var linked: HashMap<Vector2i, GlyphStack> = hashMapOf(); private set

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

    override fun toString() = "$glyph[$data] -> { inner=$inner; linked=$linked;  }"

    companion object {

        val empty = GlyphStack(EmptyGlyph)

        val dataCodec: Codec<GlyphData> = glyphCodec().dispatch({ it: GlyphData -> it.type }) { it.dataCodec() }

        val vector2iCodec: Codec<Vector2i> = RecordCodecBuilder.create { i ->
            i.group(
                Codec.INT.fieldOf("x").forGetter { it.x },
                Codec.INT.fieldOf("y").forGetter { it.y }
            ).apply(i) { x, y -> Vector2i(x, y) }
        }

        val codec: Codec<GlyphStack> = Codec.recursive("glyph_stack") { codec ->
            RecordCodecBuilder.create { x ->
                x.group(
                    dataCodec.fieldOf("glyph").forGetter(GlyphStack::data),
                    Codec.optionalField("inner", codec, false).forGetter { Optional.ofNullable(it.inner) },
                    Codec.optionalField("linked", Codec.unboundedMap(vector2iCodec, codec), false)
                        .forGetter { if(it.linked.isEmpty()) Optional.empty() else Optional.of(it.linked) }
                ).apply(x) { d, i, l ->
                    GlyphStack(d).also { it.inner = i.getOrNull(); it.linked = HashMap(l.orElseGet { mapOf() }) }
                }
            }
        }
    }
}