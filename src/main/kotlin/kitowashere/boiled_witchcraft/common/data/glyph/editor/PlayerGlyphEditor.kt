package kitowashere.boiled_witchcraft.common.data.glyph.editor

import kitowashere.boiled_witchcraft.common.data.Caps
import kitowashere.boiled_witchcraft.common.registry.GlyphTypeRegistry.GlyphGroup
import net.minecraft.world.entity.player.Player

class PlayerGlyphEditor(player: Player) : GlyphEditor(player.getCapability(Caps.Glyph.entity)!!) {
    override val groups = arrayOf(GlyphGroup.primaries, GlyphGroup.structurals)

    companion object {
        val Player.glyphEditor get() = PlayerGlyphEditor(this)
    }
}