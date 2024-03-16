package kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator

import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup.Companion.primaries
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup.Companion.structurals
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player

class PlayerGlyphConfigurator(private val player: Player) : IGlyphConfiguratorHandler {
    private var nbt: CompoundTag
        get()       = player.serializeNBT().getCompound("editing_glyph")
        set(value)  = player.deserializeNBT(player.serializeNBT().also { it.put("editing_glyph", value) })


    override val groups = arrayOf(primaries, structurals)

    override var group = groups[nbt.getInt("group")]
        set(value) { field = value; nbt = nbt.also { it.putInt("group", groups.indexOf(value)) } }

    override var glyphIndex
        get() = nbt.getInt("glyph_index")
        set(value) { nbt = nbt.also { it.putInt("glyph_index", value) } }

    override var glyph = group[glyphIndex].default().also { if (nbt.contains("glyph"))
                                                            it.deserializeNBT(nbt.getCompound("glyph")) }
        set(value) { field = value; nbt = nbt.also { it.put("glyph", glyph.serializeNBT()) } }

    override var fieldIndex = nbt.getInt("field_index")
        set(value) { field = value; nbt = nbt.also { it.putInt("field_index", value) }}

    companion object {
        val Player.glyphConfigurator get() = PlayerGlyphConfigurator(this)
    }
}