package kitowashere.boiled_witchcraft.common.world.glyph.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import kitowashere.boiled_witchcraft.client.core.glyph.EditorData.SectionRenderer
import kitowashere.boiled_witchcraft.client.render.atlas.GlyphAtlas.sprite
import kitowashere.boiled_witchcraft.common.registry.GlyphReg
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.DataField
import kitowashere.boiled_witchcraft.common.world.glyph.data.field.IntField
import kitowashere.boiled_witchcraft.common.world.glyph.type.Glyph

open class GlyphData(val glyph: Glyph) {

    private val fields = ArrayList<DataField<*>>()
    val dataFields get() = fields.toTypedArray()

    var size by IntField("size", 0, { n, _ ->
        val max = glyph.sizes.lastIndex

        if (n < 0) max
        else if (n > max) 0
        else n

    }, { "${glyph.sizes[it]}:${glyph.sizes[it]}" },
    {
        var height = 0

        SectionRenderer(
            { gui, _, font, x, y ->
                val stack = editor.stack
                val size  = stack.size

                gui.drawCenteredString(font, "${size}:${size}", x + (size * 16) / 2, y, 0xffffff)
                gui.blit(x, y + font.lineHeight + 2, 0, size * 16, size * 16, stack.sprite)

                height = font.lineHeight + 2 + size * 16
            },
            { height}
        )
    }) .persistent()

    protected fun <T> DataField<T>.persistent() = also { fields.add(it) }

    companion object {
        val codec: Codec<GlyphData> = RecordCodecBuilder.create {
            it.group(
                GlyphReg.codec       .fieldOf("glyph")  .forGetter (GlyphData::glyph),
                Codec.STRING.listOf().fieldOf("names")  .forGetter { g -> g.fields.map { f -> f.name } },
                Codec.INT.listOf()   .fieldOf("indexes").forGetter { g -> g.fields.map { f -> f.wrappedIndex } }
            ).apply(it) { g, n, i ->
                n.zip(i).toMap().run {
                    g.newData().also { d -> d.fields.forEach { f -> this[f.name]?.let { i -> f.wrappedIndex = i } } }
                }
            }
        }
    }
}