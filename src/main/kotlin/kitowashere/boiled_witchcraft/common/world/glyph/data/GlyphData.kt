package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import kitowashere.boiled_witchcraft.BoiledWitchcraft.ID
import kitowashere.boiled_witchcraft.common.registry.GlyphReg
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.IntField
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph
import net.minecraft.resources.ResourceLocation
import net.minecraft.resources.ResourceLocation.parse as loc

open class GlyphData(val glyph: Glyph) {

    private val fields = ArrayList<DataField<*>>()
    val dataFields by lazy { fields.toTypedArray() }

    var size by IntField(loc("$ID:size"), "size", 0,
        { glyph.sizes.size },
        { glyph.sizes[it]  },
        { glyph.sizes.indexOf(it) },
        { "${glyph.sizes[it]}:${glyph.sizes[it]}" }) .add()

    protected fun <T : Any> DataField<T>.add() = also { fields.add(it) }

    companion object {
        val codec: Codec<GlyphData> = RecordCodecBuilder.create {
            it.group(
                GlyphReg.codec       .fieldOf("glyph")  .forGetter (GlyphData::glyph),
                ResourceLocation.CODEC.listOf().fieldOf("fields") .forGetter { g -> g.fields.map { f -> f.location } },
                Codec.INT.listOf()   .fieldOf("indexes") .forGetter { g -> g.fields.map { f -> f.wrappedIndex } }
            ).apply(it) { g, n, i ->
                n.zip(i).toMap().run {
                    g.newData().also { d -> d.fields.forEach { f -> this[f.location]?.let { i -> f.wrappedIndex = i } } }
                }
            }
        }
    }
}