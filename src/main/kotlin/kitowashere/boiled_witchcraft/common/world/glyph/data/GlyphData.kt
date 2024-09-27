package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.google.common.collect.ImmutableList
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
    val dataFields: List<DataField<*>> by lazy { ImmutableList.copyOf(fields) }

    var size by IntField(loc("$ID:size"), "size",
        { glyph.sizes.asList() },
        { "${glyph.sizes[it]}:${glyph.sizes[it]}" }) .add()

    protected fun <T> DataField<T>.add() = also { fields.add(it) }

    companion object {
        val fieldCodec: Codec<Pair<ResourceLocation, Int>> = RecordCodecBuilder.create {
            it.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter { p -> p.first },
                Codec.INT.fieldOf("index").forGetter { p -> p.second }
            ).apply(it) { id, i -> Pair(id, i) }
        }

        val codec: Codec<GlyphData> = RecordCodecBuilder.create {
            it.group(
                GlyphReg.codec       .fieldOf("glyph")  .forGetter (GlyphData::glyph),
                fieldCodec.listOf().fieldOf("fields").forGetter { g -> g.fields.map { f -> f.location to f.wrappedIndex } },
            ).apply(it) { g, fc ->
                fc.toMap().run {
                    g.newData().also { d -> d.fields.forEach { f -> this[f.location]?.let { i -> f.wrappedIndex = i } } }
                }
            }
        }


    }
}