package kitowashere.boiled_witchcraft.common.data.handler.glyph.configurator

import kitowashere.boiled_witchcraft.common.data.handler.glyph.GlyphSelector
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry
import kitowashere.boiled_witchcraft.common.world.glyph.Glyph
import net.minecraft.world.entity.player.Player

class PlayerGlyphConfigurator(private val player: Player) : IGlyphConfiguratorHandler {

    private var glyphIndex = 0
        set(value) {
            field = value;
            glyph = GlyphTypeRegistry.Util.primaries[field].default()
        }

    override var glyph = Glyph.newFromNBT(player.serializeNBT().getCompound("editing_glyph"))
        set(value) {
            field = value
            player.deserializeNBT(player.serializeNBT().also { it.put("editing_glyph", field.serializeNBT()) })
        }

    override var fieldIndex = 0

    override fun wrapGlyph(way: GlyphSelector.WrapWay) { glyphIndex += way.value }
}